package com.curcus.lms.controller;


import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.request.CategoryRequest;
import com.curcus.lms.model.request.RatingRequest;
import com.curcus.lms.model.response.*;
import com.curcus.lms.repository.EnrollmentRepository;
import com.curcus.lms.repository.RatingRepository;
import com.curcus.lms.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.CourseException;
import com.curcus.lms.exception.UserNotFoundException;
import com.curcus.lms.model.request.RatingRequest;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.RatingResponse;
import com.curcus.lms.model.response.ResponseCode;
import com.curcus.lms.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<RatingResponse>> updateRating(
            @Valid @RequestBody RatingRequest ratingRequest,
            BindingResult bindingResult) {

        ApiResponse<RatingResponse> apiResponse = new ApiResponse<>();

        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getAllErrors().stream()
                        .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                        .collect(Collectors.joining(", "));
                System.out.println(errorMessage);
                apiResponse.error(ResponseCode.getError(1));
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }

            RatingResponse ratingResponse = ratingService.updateRating(ratingRequest);
            apiResponse.ok(ratingResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            apiResponse.error(ResponseCode.getError(8));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        } catch (CourseException c) {
            apiResponse.error(ResponseCode.getError(10));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        } catch (ApplicationException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Student has not been registered yet");
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<ApiResponse<RatingResponse>> getRatingByStudentIdAndCourseId(@PathVariable Long studentId, @PathVariable Long courseId) {
        ApiResponse<RatingResponse> apiResponse = new ApiResponse<>();
        try {
            RatingResponse ratingResponse = ratingService.getRatingByStudentIdAndCourseId(studentId, courseId);
            if (ratingResponse != null) {
                apiResponse.ok(ratingResponse);
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            } else
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            apiResponse.error(ResponseCode.getError(8));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        } catch (CourseException c) {
            apiResponse.error(ResponseCode.getError(10));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        } catch (ApplicationException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Student has not been registered yet");
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<RatingResponse>>> getRatingByCourseId(@PathVariable Long courseId) {
        ApiResponse<List<RatingResponse>> apiResponse = new ApiResponse<>();
        try {
            List<RatingResponse> ratingResponses = ratingService.getRatingByCourseId(courseId);
            apiResponse.ok(ratingResponses);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (CourseException c) {
            apiResponse.error(ResponseCode.getError(10));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        } catch (ApplicationException e) {
            apiResponse.error(ResponseCode.getError(23));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<RatingResponse>> createRating(
            @Valid @RequestBody RatingRequest ratingRequest,
            BindingResult bindingResult
    ) {
        ApiResponse<RatingResponse> apiResponse = new ApiResponse();
        try {
            if (bindingResult.hasErrors()) {
                apiResponse.error(ResponseCode.getError(1));
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }

            // check if user has enrolled the course
            if (!enrollmentRepository.existsByStudent_UserIdAndCourse_CourseId(ratingRequest.getStudentId(), ratingRequest.getCourseId())) {
                apiResponse.error(ResponseCode.getError(24));
                return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
            }

            RatingResponse response = ratingService.createRating(ratingRequest);
            apiResponse.ok(response);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

        } catch(Exception e) {
            apiResponse.error(ResponseCode.getError(23));
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
