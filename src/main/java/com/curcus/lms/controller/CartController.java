package com.curcus.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.service.CartService;
import com.curcus.lms.service.CourseService;
import com.curcus.lms.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/addCourse")
    public ResponseEntity<ApiResponse<CartItems>> addCourseToCart(@RequestParam Long studentId,
            @RequestParam Long courseId) {
        try {
            CartItems cartItems = cartService.addCourseToCart(studentId, courseId);
            ApiResponse apiResponse = new ApiResponse<>();
            apiResponse.ok(cartItems);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (ValidationException ex) {
            throw ex;
        }
    }

}
