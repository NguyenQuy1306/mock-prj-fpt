package com.curcus.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.request.ContentCreateRequest;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.request.SectionRequest;

import com.curcus.lms.model.response.ContentCreateResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.SectionCreateResponse;

import jakarta.validation.Valid;

public interface CourseService {

    List<CourseResponse> findAll();

    CourseResponse deleteCourse(Long id);

    SectionCreateResponse createSection(SectionRequest sectionRequest);

    List<CourseResponse> findByCategory(int categoryId);

    CourseResponse saveCourse(CourseCreateRequest courseCreateRequest);
    
//    Page<CourseResponse> searchCoursesByName(String name,Pageable pageable);
//    Page<CourseResponse> searchCourses(Long instructorId, Long categoryId, String title, Long minPrice, Long maxPrice, Pageable pageable);
    Page<CourseResponse> searchCourses(Long instructorId, Long categoryId, String title, Long price, Pageable pageable);

    ContentCreateResponse saveContent(ContentCreateRequest contentCreateRequest);
    
}
