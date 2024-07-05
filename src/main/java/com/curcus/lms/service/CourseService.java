package com.curcus.lms.service;

import java.util.List;

import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.request.VideoContentCreateRequest;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.VideoContentCreateResponse;

import jakarta.validation.Valid;

public interface CourseService {

    List<CourseResponse> findAll();

    List<CourseResponse> findByCategory(int categoryId);
    
    CourseResponse saveCourse(CourseCreateRequest courseCreateRequest);
    

	VideoContentCreateResponse saveVideoContent(VideoContentCreateRequest videoContentCreateRequest) ;
}
