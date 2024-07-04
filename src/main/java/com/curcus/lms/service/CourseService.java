package com.curcus.lms.service;

import java.util.List;

import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.response.CourseResponse;

public interface CourseService {

    List<CourseResponse> findAll();

    List<CourseResponse> findByCategory(int categoryId);
    
    CourseResponse saveCourse(CourseCreateRequest courseCreateRequest);
}
