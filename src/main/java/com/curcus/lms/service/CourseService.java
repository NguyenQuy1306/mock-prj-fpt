package com.curcus.lms.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.response.CourseResponse;

public interface CourseService {

    List<CourseResponse> findAll();

    Page<CourseResponse> findByCategory(Long categoryId, Pageable pageable);

    Page<CourseResponse> findAll(Pageable pageable);

//    List<CourseResponse> findByCategory(Long categoryId);

    Course findById(Long id);

    Instructor findByIdInstructor(Long id);

    CourseResponse update(CourseRequest courseRequest, BindingResult bindingResult);
}
