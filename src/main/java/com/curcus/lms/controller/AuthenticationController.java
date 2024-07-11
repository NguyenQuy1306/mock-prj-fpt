package com.curcus.lms.controller;


import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.response.*;
import com.curcus.lms.exception.IncorrectPasswordException;
import com.curcus.lms.model.request.RegisterRequest;
import com.curcus.lms.exception.UserNotFoundException;
import com.curcus.lms.model.request.AuthenticationRequest;
import com.curcus.lms.repository.UserRepository;
import com.curcus.lms.repository.VerificationTokenRepository;
import com.curcus.lms.service.AuthenticationService;
import com.curcus.lms.service.impl.CookieServiceImpl;
import com.curcus.lms.service.impl.EmailServiceImpl;
import com.curcus.lms.service.impl.VerificationTokenServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final EmailServiceImpl emailServiceImpl;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final VerificationTokenServiceImpl verificationTokenServiceImpl;
    private final CookieServiceImpl cookieServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> register(@Valid @RequestBody RegisterRequest request,
                                                         BindingResult bindingResult) {

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        if (bindingResult.hasErrors()) {
//            String errorMessage = bindingResult.getAllErrors().stream()
//                    .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
//                    .collect(Collectors.joining(", "));
            apiResponse.error(ResponseCode.getError(1));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()
                    || userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
                apiResponse.error(ResponseCode.getError(2));
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }
            if (!service.register(request)) {
                apiResponse.error(ResponseCode.getError(23));
                return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            boolean emailSent = false;
            String successMessage = "";

            if ("S".equals(request.getUserRole())) {
                emailSent = emailServiceImpl.sendEmailToStudent(request.getEmail());
                successMessage = "Đăng ký thành công. Vui lòng kiểm tra email để hoàn thành xác nhận tài khoản. Nếu bạn không nhận được email, ấn vào đây.";
            } else if ("I".equals(request.getUserRole())) {
                emailSent = emailServiceImpl.sendEmailToInstructor(request.getEmail());
                successMessage = "Đăng ký thành công. Vui lòng kiểm tra email.";
            }

            if (emailSent) {
                apiResponse.ok(true);
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            } else {
                apiResponse.error(ResponseCode.getError(21));
                return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            apiResponse.error(ResponseCode.getError(23));
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<Boolean>> authenticate(
            @Valid @RequestBody AuthenticationRequest request,
            BindingResult bindingResult,
            HttpServletResponse response
    ) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            apiResponse.error(ResponseCode.getError(1));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            AuthenticationResponse tokens = service.authenticate(request);
            if (!(cookieServiceImpl.addCookie(response,
                    "accessToken",
                    tokens.getAccessToken()).orElse(false)
                    && cookieServiceImpl.addCookie(response,
                    "refreshToken",
                    tokens.getRefreshToken()).orElse(false))) {
                apiResponse.error(ResponseCode.getError(23));
                return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            System.out.println(tokens.getAccessToken());
            System.out.println(tokens.getRefreshToken());
            apiResponse.ok(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            apiResponse.error(ResponseCode.getError(8));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        } catch (IncorrectPasswordException e) {
            apiResponse.error(ResponseCode.getError(9));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/is-expired-verification")
    public ResponseEntity<Object> isExpiredVerification(@RequestParam String token) {
        try {
            User user = verificationTokenServiceImpl.validateVerificationToken(token)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            user.setActivated(true);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Token is not expired and now is revoked");
//            return new RedirectView("/api/v1/auth/successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//            return new RedirectView("/api/v1/auth/unsuccessful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//            return new RedirectView("/api/v1/auth/unsuccessful");

        }
    }

//    @GetMapping("/successful")
//    public ResponseEntity<String> successfulAuthentication() {
//        return ResponseEntity.status(HttpStatus.OK).body("Successful authentication");
//    }
//
//    @GetMapping("/unsuccessful")
//    public ResponseEntity<String> unsuccessfulAuthentication() {
//        return ResponseEntity.status(HttpStatus.OK).body("Unsuccessful authentication");
//    }

}
