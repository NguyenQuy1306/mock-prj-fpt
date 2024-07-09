package com.curcus.lms.controller;

import com.curcus.lms.model.request.EmailRequest;
import com.curcus.lms.service.impl.PasswordResetImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetController {
    @Autowired
    PasswordResetImpl passwordReset;
    @PostMapping("/request")
    public ResponseEntity<Boolean> request(@RequestBody EmailRequest emailRequest) {
        return ResponseEntity.ok(passwordReset.requestPasswordReset(emailRequest.getEmail()));
    }

    @GetMapping("/reset")
    public RedirectView reset(@RequestParam String token) {
        Boolean result = passwordReset.resetPassword(token);
        if (result) {
            return new RedirectView("http://localhost:8080/password-reset/successful");
        } else {
            return new RedirectView("http://localhost:8080/password-reset/failed");
        }
    }

    // sẽ redirect qua bên front-end (một static page với message bên dưới), hiện tại đang để tạm như vậy
    @GetMapping("/successful")
    public ResponseEntity<String> successful() {
        return ResponseEntity.ok("Reset mật khẩu thành công. Một email chứa mật khẩu mới đã được gửi vào email của bạn.");
    }

    // sẽ redirect qua bên front-end (một static page với message bên dưới), hiện tại đang để tạm như vậy
    @GetMapping("/failed")
    public ResponseEntity<String> unsuccessful() {
        return ResponseEntity.ok("Đã xảy ra lỗi");
    }
}
