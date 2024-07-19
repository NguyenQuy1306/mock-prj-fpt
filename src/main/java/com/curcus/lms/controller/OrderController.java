package com.curcus.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curcus.lms.model.request.CheckoutReq;
import com.curcus.lms.model.request.PurchaseOrderDTO;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.CheckoutResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.PaymentResponse;
import com.curcus.lms.service.CourseService;
import com.curcus.lms.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/orders")
@Validated
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<CheckoutResponse>> checkoutOrder(@NotNull @RequestBody CheckoutReq checkoutReq) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.ok(orderService.checkoutOrder(checkoutReq));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/processingPurchase")
    public ResponseEntity<ApiResponse<PaymentResponse.VNPayResponse>> postMethodName(
            @Valid @RequestBody PurchaseOrderDTO purchaseOrderDTO, HttpServletRequest request) {
        PaymentResponse.VNPayResponse paymentResponse = orderService.processingPurchaseOrder(purchaseOrderDTO, request);
        ApiResponse<PaymentResponse.VNPayResponse> apiResponse = new ApiResponse<>();
        apiResponse.ok(paymentResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
