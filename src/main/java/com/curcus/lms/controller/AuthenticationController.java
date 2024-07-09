package com.curcus.lms.controller;


import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.entity.VerificationToken;
import com.curcus.lms.model.response.ErrorResponse;
import com.curcus.lms.exception.IncorrectPasswordException;
import com.curcus.lms.auth.RegisterRequest;
import com.curcus.lms.exception.UserNotFoundException;
import com.curcus.lms.model.request.AuthenticationRequest;
import com.curcus.lms.model.response.AuthenticationResponse;
import com.curcus.lms.repository.UserRepository;
import com.curcus.lms.repository.VerificationTokenRepository;
import com.curcus.lms.service.AuthenticationService;
import com.curcus.lms.service.JwtService;
import com.curcus.lms.service.impl.EmailServiceImpl;
import com.curcus.lms.service.impl.VerificationTokenServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final EmailServiceImpl emailService;
    private final JwtService jwtService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final VerificationTokenServiceImpl verificationTokenService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
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

    @GetMapping("/confirmEmail/{email}")
    public ResponseEntity<Boolean> sendEmail(@PathVariable String email) {
        String token = verificationTokenService.createVerificationToken(email).orElseThrow();
        if (token != null) {
            // create email content
            String recipient = email;
            String subject = "Xác nhận địa chỉ email của bạn";
            String template = "<p>Dear " + email + ",</p>"
                    + "<p>Để xác thực địa chỉ email đã đăng ký vui lòng ấn " + "<a href=\"http://localhost:8080/api/v1/auth/is-expired-verification?token=" + token + "\">link text</a>" +".</p>"
                    + "<p>Best regards,</p>"
                    + "<p>FSA Backend</p>";
            return ResponseEntity.ok(emailService.sendEmail(recipient, subject, template));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

    }

    @GetMapping("/is-expired-verification")
    public RedirectView isExpiredVerification(@RequestParam String token) {
        try {
            VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

            if (verificationToken.isRevoked()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has been verified");
                return new RedirectView("/api/v1/auth/unsuccessful");

            }

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiredDate = verificationToken.getIssueAt().plusDays(1);

            if (now.isAfter(expiredDate)) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired");
                return new RedirectView("/api/v1/auth/unsuccessful");

            }

            verificationToken.setRevoked(true);
            verificationTokenRepository.save(verificationToken);

            User user = userRepository.findById(verificationToken.getUser().getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.setActivated(true);
            userRepository.save(user);

//            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Token is not expired and now is revoked");
            return new RedirectView("/api/v1/auth/successful");
        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            return new RedirectView("/api/v1/auth/unsuccessful");
        } catch (Exception e) {
            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
            return new RedirectView("/api/v1/auth/unsuccessful");

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
