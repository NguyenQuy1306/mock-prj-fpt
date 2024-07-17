package com.curcus.lms.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.curcus.lms.config.VNPayConfig;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.entity.Cart;
import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.request.PurchaseOrderDTO;
import com.curcus.lms.model.response.CheckoutResponse;
import com.curcus.lms.model.response.PaymentResponse;
import com.curcus.lms.repository.CartRepository;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.repository.UserRepository;
import com.curcus.lms.service.CourseService;
import com.curcus.lms.service.OrderService;
import com.curcus.lms.service.PaymentService;
import com.curcus.lms.util.VNPayUtil;
import com.curcus.lms.exception.ValidationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final CourseRepository courseRepository;
    private final PaymentService paymentService;
    private final VNPayConfig vnPayConfig;
    private final StudentRepository studentRepository;
    private final CartRepository cartRepository;

    @Override
    public CheckoutResponse checkoutOrder(Long[] idCourses) {
        long totalPrice = 0;
        for (long idCourse : idCourses) {
            Course course = courseRepository.findById(idCourse).orElseThrow(() -> new NotFoundException(
                    "please update cart"));
            totalPrice += course.getPrice();
        }
        return CheckoutResponse.builder().totalPrice(totalPrice).build();
    }

    @Override
    public PaymentResponse.VNPayResponse processingPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO,
            HttpServletRequest request) {
        long idFake = 1;
        StringBuilder orderInfo = new StringBuilder();
        orderInfo.append(idFake + "##");
        for (Long idCourse : purchaseOrderDTO.getIdCourses()) {
            orderInfo.append(idCourse + "#");
        }
        CheckoutResponse checkoutResponse = checkoutOrder(purchaseOrderDTO.getIdCourses());
        if (checkoutResponse.getTotalPrice() != purchaseOrderDTO.getTotalPrice()) {
            throw new NotFoundException("please update cart");
        }
        return paymentService.createVnPayPayment(request, checkoutResponse.getTotalPrice(), orderInfo.toString());
    }

    @Override
    public void completeOrder(Map<String, String> reqParams) {
        String vnp_SecureHash = reqParams.remove("vnp_SecureHash");
        if (vnp_SecureHash == null) {
            throw new NotFoundException("vnp_SecureHash is required");
        }
        String hashData = VNPayUtil.getPaymentURL(reqParams, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        if (!vnpSecureHash.equals(vnp_SecureHash)) {
            throw new ValidationException("vnp_SecureHash is invalid");
        }
        String[] infoOrder = reqParams.get("vnp_OrderInfo").split("##");
        long idUser = Long.parseLong(infoOrder[0]);
        List<String> idCourses = Arrays.asList(infoOrder[1].split("#"));
        Cart cart = new Cart();
        Student user = studentRepository.findById(idUser).orElseThrow(() -> new NotFoundException("user not found"));
        cart.setStudent(user);
        for (String idCourse : idCourses) {
            Course course = courseRepository.findById(Long.parseLong(idCourse))
                    .orElseThrow(() -> new NotFoundException("course not found"));
            CartItems cartItems = new CartItems();
            cartItems.setCart(cart);
            cartItems.setCourse(course);
        }
        cartRepository.save(cart);
        // xử thông tin thanh toán
    }

}
