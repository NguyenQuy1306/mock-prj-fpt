package com.curcus.lms.controller;


import com.curcus.lms.model.response.ErrorResponse;
import com.curcus.lms.exception.IncorrectPasswordException;
import com.curcus.lms.auth.RegisterRequest;
import com.curcus.lms.exception.UserNotFoundException;
import com.curcus.lms.model.request.AuthenticationRequest;
import com.curcus.lms.model.response.AuthenticationResponse;
import com.curcus.lms.service.AuthenticationService;
import com.curcus.lms.service.impl.EmailServiceImpl;
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
    private final EmailServiceImpl emailService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(
            @Valid @RequestBody AuthenticationRequest request,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(new ErrorResponse("MSG1", errorMessage));
        }

        try {
            return ResponseEntity.ok(service.authenticate(request));
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

    @GetMapping("/confirmEmail/{mail}")
    public ResponseEntity<Boolean> sendEmail(@PathVariable String mail) {
        String recipient = mail;
        String subject = "Xác nhận địa chỉ email của bạn";
        String template = "<p>Dear " + mail + ",</p>"
                + "<p>Để xác thực địa chỉ email đã đăng ký vui lòng ấn" + "<a href=\"google.com\">link text</a>" +".</p>"
                + "<p>Best regards,</p>"
                + "<p>FSA Backend</p>";

        return ResponseEntity.ok(emailService.sendEmail(recipient, subject, template));
    }

}
