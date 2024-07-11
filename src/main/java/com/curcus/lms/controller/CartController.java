package com.curcus.lms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.StudentResponse;
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

    @PostMapping(value = "/{Id}/cart")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getListCourseFromCart(@PathVariable Long studentId) {
        try {
            List<CourseResponse> courseResponses = cartService.getListCourseFromCart(studentId);
            ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(courseResponses);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (ValidationException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
