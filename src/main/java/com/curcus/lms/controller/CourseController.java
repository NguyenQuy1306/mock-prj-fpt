package com.curcus.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/api/courses")

public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(value = { "", "/list" })
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses(
            @RequestParam(value = "category", required = false) String category) {
        try {
            List<CourseResponse> coures = null;
            if (category == null) {
                coures = courseService.findAll();
            } else {
                coures = courseService.findByCategory(Integer.parseInt(category));
            }
            ApiResponse apiResponse = new ApiResponse<>();
            apiResponse.ok(coures);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

}
