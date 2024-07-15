package com.curcus.lms.service;

import org.springframework.validation.BindingResult;

import com.curcus.lms.model.entity.Cart;
import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.request.CourseRequest;

public interface CartService {
    CartItems addCourseToCart(Long studentId, Long courseId);

    CartItems getById(Long cartId, Long courseId);

    Cart getCartById(Long studentId);

<<<<<<< HEAD
    Cart createCart(Long studentId);

=======
    void deleteCourseFromCart(Long studentId, Long cartId, Long courseId);

    void deleteAllCourseFromCart(Long studentId, Long cartId);

    void deleteCart(Long studentId);
>>>>>>> origin/studentDeleteCourseFromCart
}
