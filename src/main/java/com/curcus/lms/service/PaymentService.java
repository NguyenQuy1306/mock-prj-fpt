package com.curcus.lms.service;

import com.curcus.lms.model.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    public PaymentResponse.VNPayResponse createVnPayPayment(HttpServletRequest request);
}
