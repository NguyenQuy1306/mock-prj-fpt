package com.curcus.lms.service;

import com.curcus.lms.model.request.RatingRequest;
import com.curcus.lms.model.response.RatingResponse;

import java.util.List;

public interface RatingService {
    RatingResponse updateRating(RatingRequest ratingRequest);

    RatingResponse getRatingByStudentIdAndCourseId(Long studentId, Long courseId);

    List<RatingResponse> getRatingByCourseId(Long courseId);
}
