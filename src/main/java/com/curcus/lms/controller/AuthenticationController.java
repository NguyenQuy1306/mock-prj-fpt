package com.curcus.lms.controller;


import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.response.ErrorResponse;
import com.curcus.lms.exception.IncorrectPasswordException;
import com.curcus.lms.model.request.RegisterRequest;
import com.curcus.lms.exception.UserNotFoundException;
import com.curcus.lms.model.request.AuthenticationRequest;
import com.curcus.lms.model.response.AuthenticationResponse;
import com.curcus.lms.model.response.SuccessfulResponse;
import com.curcus.lms.repository.UserRepository;
import com.curcus.lms.repository.VerificationTokenRepository;
import com.curcus.lms.service.AuthenticationService;
import com.curcus.lms.service.impl.EmailServiceImpl;
import com.curcus.lms.service.impl.VerificationTokenServiceImpl;
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

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request,
                                           BindingResult bindingResult) {
        System.out.println(bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
//            String errorMessage = bindingResult.getAllErrors().stream()
//                    .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
//                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(new ErrorResponse("MSG1", "Vui lòng điền đầy đủ thông tin."));
        }
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()
            || userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("MSG2", "Người dùng đã tồn tại trong hệ thống."));
            }
            if (!service.register(request)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("MSG (?)", "Can't create account"));
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
                return ResponseEntity.ok(new SuccessfulResponse("MGS7", successMessage));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("MSG 21", "Đã xảy ra lỗi. Vui lòng thử lại sau."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("MSG (?)", "Lỗi chưa xác định."));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(
            @Valid @RequestBody AuthenticationRequest request,
            BindingResult bindingResult,
            HttpServletResponse response
    ) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(new ErrorResponse("MSG1", errorMessage));
        }

        try {
            AuthenticationResponse tokens = service.authenticate(request);
//            Cookie accessTokenCookie = new Cookie("accessToken", tokens.getAccessToken());
//            accessTokenCookie.setHttpOnly(true);
//            accessTokenCookie.setSecure(true); // Set to true in production
//            accessTokenCookie.setPath("/");
//            accessTokenCookie.setMaxAge(86400000);
//
//            Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.getRefreshToken());
//            refreshTokenCookie.setHttpOnly(true);
//            refreshTokenCookie.setSecure(true); // Set to true in production
//            refreshTokenCookie.setPath("/");
//            refreshTokenCookie.setMaxAge(604800000);
            return ResponseEntity.ok(tokens);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("MSG8", "Account does not exist"));
        } catch (IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("MSG9", "Incorrect password"));
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

    @GetMapping("/successful")
    public ResponseEntity<String> successfulAuthentication() {
        return ResponseEntity.status(HttpStatus.OK).body("Successful authentication");
    }

    @GetMapping("/unsuccessful")
    public ResponseEntity<String> unsuccessfulAuthentication() {
        return ResponseEntity.status(HttpStatus.OK).body("Unsuccessful authentication");
    }

}
