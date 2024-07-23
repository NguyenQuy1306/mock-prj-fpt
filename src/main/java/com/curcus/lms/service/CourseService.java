package com.curcus.lms.service;

import java.util.List;

import org.springframework.validation.BindingResult;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.request.ContentCreateRequest;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.request.SectionRequest;

import com.curcus.lms.model.response.ContentCreateResponse;
import com.curcus.lms.model.response.CourseDetailResponse2;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.SectionCreateResponse;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface CourseService {
    // List<CourseResponse> findByCategory(Long categoryId);

    CourseResponse deleteCourse(Long id);

    SectionCreateResponse createSection(SectionRequest sectionRequest);

    Page<CourseResponse> findByCategory(Long categoryId, Pageable pageable);

    Page<CourseResponse> findAll(Pageable pageable);

    CourseResponse saveCourse(CourseCreateRequest courseCreateRequest);
    
//    Page<CourseResponse> searchCoursesByName(String name,Pageable pageable);
//    Page<CourseResponse> searchCourses(Long instructorId, Long categoryId, String title, Long minPrice, Long maxPrice, Pageable pageable);
    Page<CourseResponse> searchCourses(Long instructorId, Long categoryId, String title, Long price, Pageable pageable);

    ContentCreateResponse saveContent(ContentCreateRequest contentCreateRequest);

    Course findById(Long id);

    Instructor findByIdInstructor(Long id);

    CourseResponse update(CourseRequest courseRequest, BindingResult bindingResult);

    void checkCourseRequest(CourseRequest courseRequest, BindingResult bindingResult);

    SectionCreateResponse updateSection(Long sectionId, SectionRequest sectionRequest);

    // CourseResponse update(CourseRequest courseRequest, BindingResult
    // bindingResult);
    List<CourseDetailResponse2> getCoursebyInstructorId(Long id);
}
