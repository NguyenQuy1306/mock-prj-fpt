package com.curcus.lms.service;

import java.util.List;
import java.util.Optional;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.request.*;

import com.curcus.lms.model.response.ContentCreateResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.SectionCreateResponse;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface CourseService {


    CourseResponse deleteCourse(Long id);

    SectionCreateResponse createSection(SectionRequest sectionRequest);

    Page<CourseResponse> findByCategory(Long categoryId, Pageable pageable);

    Page<CourseResponse> findAll(Pageable pageable);

    CourseResponse saveCourse(CourseCreateRequest courseCreateRequest);

    ContentCreateResponse saveContent(ContentCreateRequest contentCreateRequest);

    Course findById(Long id);

    Instructor findByIdInstructor(Long id);

    SectionCreateResponse updateSection(SectionUpdateRequest sectionUpdateRequest);

    CourseResponse update(CourseRequest courseRequest, BindingResult bindingResult);

}
