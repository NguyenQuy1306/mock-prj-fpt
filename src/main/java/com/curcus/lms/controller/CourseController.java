package com.curcus.lms.controller;

import com.curcus.lms.model.response.MetadataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping(value = {"", "/list"})
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses(
            @RequestParam(value = "category", required = false) Long category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CourseResponse> coursePage;

            if (category == null) {
                coursePage = courseService.findAll(pageable);
            } else {
                coursePage = courseService.findByCategory(category, pageable);
            }

            if (coursePage.isEmpty()) {
                throw new NotFoundException("Course not found.");
            }

            MetadataResponse metadata = new MetadataResponse(
                    coursePage.getTotalElements(),
                    coursePage.getTotalPages(),
                    coursePage.getNumber(),
                    coursePage.getSize(),
                    (coursePage.hasNext() ? "/api/courses/list?page=" + (coursePage.getNumber() + 1) : null),
                    (coursePage.hasPrevious() ? "/api/courses/list?page=" + (coursePage.getNumber() - 1) : null),
                    "/api/courses/list?page=" + (coursePage.getTotalPages() - 1),
                    "/api/courses/list?page=0"
            );

            ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(coursePage.getContent(), metadata);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @PutMapping(value = "/{course_id}/updateCourse")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@PathVariable Long course_id,
            @Valid @RequestBody CourseRequest courseRequest,
            BindingResult bindingResult) {
        try {
            if (course_id != courseRequest.getCourseId()) {
                throw new ValidationException("CourseId for parameter of api updateCourse is wrong");
            }
            CourseResponse courseResponse = courseService.update(courseRequest, bindingResult);
            ApiResponse apiResponse = new ApiResponse<>();
            apiResponse.ok(courseResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }


}
