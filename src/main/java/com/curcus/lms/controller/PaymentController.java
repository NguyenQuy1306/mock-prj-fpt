package com.curcus.lms.controller;

import com.curcus.lms.model.response.PaymentResponse;
import com.curcus.lms.service.impl.OrderServiceImpl;
import com.curcus.lms.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentServiceImpl paymentService;
    private final OrderServiceImpl orderService;

    // @GetMapping("/vn-pay")
    // public ResponseEntity<PaymentResponse.VNPayResponse> pay(HttpServletRequest
    // request) {
    // return new
    // ResponseEntity<>(paymentService.createVnPayPayment(request),HttpStatus.OK);
    // }
    @GetMapping("/vn-pay-callback")
    public ResponseEntity<PaymentResponse.VNPayResponse> payCallbackHandler(@RequestParam Map<String, String> reqParams,
            HttpServletResponse response) throws IOException {
        orderService.completeOrder(reqParams);
        String status = reqParams.get("vnp_ResponseCode");
        if (status.equals("00")) {
            return new ResponseEntity<>(new PaymentResponse.VNPayResponse("00", "Success", ""), HttpStatus.OK);
            // response.sendRedirect("http://localhost:3000/payment-success");
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            // response.sendRedirect("http://localhost:3000/payment-failed");
        }
    }
}