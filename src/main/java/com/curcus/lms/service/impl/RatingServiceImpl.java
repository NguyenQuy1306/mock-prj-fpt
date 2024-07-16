package com.curcus.lms.service.impl;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.CourseException;
import com.curcus.lms.exception.UserNotFoundException;
import com.curcus.lms.model.entity.Rating;
import com.curcus.lms.model.mapper.OthersMapper;
import com.curcus.lms.model.request.RatingRequest;
import com.curcus.lms.model.response.RatingResponse;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.RatingRepository;
import com.curcus.lms.repository.UserRepository;
import com.curcus.lms.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private OthersMapper othersMapper;
    @Override
    public RatingResponse updateRating(RatingRequest ratingRequest) {
        try {
            if (!userRepository.existsById(ratingRequest.getStudentId()))
                throw new UserNotFoundException("Student not found");
            if (!courseRepository.existsById(ratingRequest.getCourseId()))
                throw new CourseException("Course not found");
            Rating newRating = ratingRepository
                    .findByStudent_UserIdAndCourse_CourseId(
                            ratingRequest.getStudentId(),
                            ratingRequest.getCourseId()
                    );
            if (newRating != null) {
                newRating.setRating(ratingRequest.getRating());
                newRating.setComment(ratingRequest.getComment());

                return othersMapper.toRatingResponse(ratingRepository.save(newRating));
            } else
                throw new ApplicationException("This student has not registered this course");

        } catch (ApplicationException e) {
            throw e;
        }
    }

    @Override
    public RatingResponse getRatingByStudentIdAndCourseId(Long studentId, Long courseId) {
        try {
            if (!userRepository.existsById(studentId))
                throw new UserNotFoundException("Student not found");
            if (!courseRepository.existsById(courseId))
                throw new CourseException("Course not found");
            Rating newRating = ratingRepository
                    .findByStudent_UserIdAndCourse_CourseId(
                            studentId,
                            courseId
                    );
            if (newRating != null) {
                return othersMapper.toRatingResponse(newRating);
            } else
                return null;
        } catch (ApplicationException e) {
            throw e;
        }
    }

    @Override
    public List<RatingResponse> getRatingByCourseId(Long courseId) {
        try {
            if (!courseRepository.existsById(courseId))
                throw new CourseException("Course not found");
            return othersMapper.toRatingResponseList(ratingRepository.findAllByCourse_CourseId(courseId));
        }
        catch (ApplicationException e) {
            throw e;
        }
    }
}
