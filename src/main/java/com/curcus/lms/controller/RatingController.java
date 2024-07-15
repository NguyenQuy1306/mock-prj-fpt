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

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private RatingService ratingService;

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
