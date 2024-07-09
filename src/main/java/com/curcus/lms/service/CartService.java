package com.curcus.lms.service;

import org.springframework.validation.BindingResult;

import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.request.CourseRequest;

public interface CartService {
    CartItems addCourseToCart(CourseRequest courseRequest, BindingResult bindingResult);
}
