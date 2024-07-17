package com.curcus.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.service.CategorySevice;
import com.curcus.lms.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategorySevice categorySevice;

    @GetMapping("")
    public ResponseEntity<ApiResponse<CourseResponse>> getAllCategories() {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.ok(categorySevice.getAllCategory());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
