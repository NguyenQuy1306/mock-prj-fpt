package com.curcus.lms.service;

import org.springframework.validation.BindingResult;

import com.curcus.lms.model.entity.Cart;
import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.response.CartItemsResponse;
import com.curcus.lms.model.response.CartResponse;
import com.curcus.lms.model.response.CourseResponse;

import java.util.List;

public interface CartService {

    CartItems addCourseToCart(Long studentId, Long courseId);

    Cart getCartById(Long studentId);

    CartItems getById(Long cartId, Long courseId);

    CartResponse getById(Long cartId);

    CartResponse getCartByStudentId(Long studentId);

    Cart createCart(Long studentId);

    void deleteCourseFromCart(Long studentId, Long courseId);

    void deleteAllCourseFromCart(Long studentId);

    void deleteCart(Long studentId);

    List<CourseResponse> getListCourseFromCart(Long studentId);

    void copyCartToOrder(Long studentId);
}
