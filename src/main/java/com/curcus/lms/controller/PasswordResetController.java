package com.curcus.lms.controller;

import com.curcus.lms.model.request.PasswordResetRequest;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.ResponseCode;
import com.curcus.lms.service.impl.PasswordResetImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetController {
    @Autowired
    PasswordResetImpl passwordReset;
    @PostMapping("/request")
    public ResponseEntity<ApiResponse<Boolean>> request(@RequestBody PasswordResetRequest emailRequest) {
//        return ResponseEntity.ok(passwordReset.requestPasswordReset(emailRequest.getEmail()));
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        try {
            Boolean result = passwordReset.requestPasswordReset(emailRequest.getEmail());
            if (result) {
                apiResponse.ok(true);
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            } else {
                apiResponse.error(ResponseCode.getError(8));
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            apiResponse.error(ResponseCode.getError(23));
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
