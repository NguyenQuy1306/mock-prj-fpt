package com.curcus.lms.controller;

import com.curcus.lms.model.request.PasswordResetRequest;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.ResponseCode;
import com.curcus.lms.service.impl.PasswordResetImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {
    @Autowired
    PasswordResetImpl passwordReset;
    @PostMapping("/request")
    public ResponseEntity<ApiResponse<Boolean>> request(@Valid @RequestBody PasswordResetRequest emailRequest,
                                                        BindingResult bindingResult) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        try {
            if (bindingResult.hasErrors()) {
                apiResponse.error(ResponseCode.getError(1));
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }
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
            return new RedirectView("http://localhost:8080/api/password-reset/successful");
        } else {
            return new RedirectView("http://localhost:8080/api/password-reset/failed");
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
