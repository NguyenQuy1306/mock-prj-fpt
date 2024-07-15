package com.curcus.lms.service;

import com.curcus.lms.model.request.RatingRequest;
import com.curcus.lms.model.response.RatingResponse;

public interface RatingService {
    RatingResponse createRating(RatingRequest ratingRequest);
}
