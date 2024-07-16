package com.curcus.lms.service;

import java.util.List;
import java.util.Optional;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.request.ContentCreateRequest;
import com.curcus.lms.model.request.ContentUpdatePositionRequest;
import com.curcus.lms.model.request.ContentUpdateRequest;
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

    ContentCreateResponse saveContent(ContentCreateRequest contentCreateRequest);
    
    ContentCreateResponse updateContent(Long id, @Valid ContentUpdateRequest contentUpdateRequest);

    List<ContentCreateResponse> updateContentPositions(Long id, @Valid List<ContentUpdatePositionRequest> positionUpdates);
}
