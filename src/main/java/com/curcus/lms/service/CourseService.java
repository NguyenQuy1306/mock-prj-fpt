package com.curcus.lms.service;

import java.util.List;
import java.util.Optional;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.request.ContentCreateRequest;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.request.SectionRequest;

import com.curcus.lms.model.response.ContentCreateResponse;
import com.curcus.lms.model.response.CourseResponse;


import jakarta.validation.Valid;

public interface CourseService {

    List<CourseResponse> findAll();

    CourseResponse deleteCourse(Long id);

    Section createSection(SectionRequest sectionRequest);

    List<CourseResponse> findByCategory(int categoryId);

    CourseResponse saveCourse(CourseCreateRequest courseCreateRequest);

    ContentCreateResponse saveContent(ContentCreateRequest contentCreateRequest);
    
}
