package com.curcus.lms.controller;


import com.curcus.lms.model.response.PaymentResponse;
import com.curcus.lms.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentServiceImpl paymentService;
    @GetMapping("/vn-pay")
    public ResponseEntity<PaymentResponse.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseEntity<>(paymentService.createVnPayPayment(request),HttpStatus.OK);
    }
    @GetMapping("/vn-pay-callback")
    public ResponseEntity<PaymentResponse.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return new ResponseEntity<>(new PaymentResponse.VNPayResponse("00", "Success", ""), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST );
        }
    }
}