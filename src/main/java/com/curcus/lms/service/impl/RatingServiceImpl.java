package com.curcus.lms.service.impl;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Rating;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.mapper.OthersMapper;
import com.curcus.lms.model.request.RatingRequest;
import com.curcus.lms.model.response.RatingResponse;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.RatingRepository;
import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private OthersMapper othersMapper;

    @Override
    public RatingResponse createRating(RatingRequest ratingRequest) {
        Student student = studentRepository.findById(ratingRequest.getStudentId()).orElseThrow();
        Course course = courseRepository.findById(ratingRequest.getCourseId()).orElseThrow();
        Rating rating = new Rating();
        rating.setStudent(student);
        rating.setCourse(course);
        rating.setComment(ratingRequest.getComment());
        rating.setRating(ratingRequest.getRating());
        return othersMapper.toRatingResponse(ratingRepository.save(rating));
    }
}
