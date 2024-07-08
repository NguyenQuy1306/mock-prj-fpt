package com.curcus.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.service.CourseService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/courses")

public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseMapper courseMapper;

    @GetMapping(value = { "", "/list" })
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses(
            @RequestParam(value = "category", required = false) String category) {
        try {
            List<CourseResponse> course = null;
            if (category == null) {
                course = courseService.findAll();
            } else {
                course = courseService.findByCategory(Integer.parseInt(category));
            }
            if (course.size() == 0) {
                throw new NotFoundException("Course not found.");
            }
            ApiResponse apiResponse = new ApiResponse<>();
            apiResponse.ok(course);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@RequestParam Long courseId) {
        try {
            Course course = courseService.findById(courseId);
            if (course == null) {
                throw new NotFoundException("Course not found");
            }
            CourseResponse courseResponse = courseMapper.toResponse(course);
            ApiResponse apiResponse = new ApiResponse<>();
            apiResponse.ok(courseResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @PutMapping(value = "/updateCourse")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@Valid @RequestBody CourseRequest courseRequest,
            BindingResult bindingResult) {
        try {
            CourseResponse courseResponse = courseService.update(courseRequest, bindingResult);
            ApiResponse apiResponse = new ApiResponse<>();
            apiResponse.ok(courseResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
            // other exception throw application() (not exception of user)
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

}
