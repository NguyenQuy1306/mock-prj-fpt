package com.curcus.lms.controller;

import com.curcus.lms.model.response.MetadataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.SectionCreateResponse;
import com.curcus.lms.model.response.ContentCreateResponse;
import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.request.SectionRequest;
import com.curcus.lms.model.request.ContentCreateRequest;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.service.CourseService;

import jakarta.validation.Valid;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/courses")
@Validated
@CrossOrigin(origins = "*")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseMapper courseMapper;

    @GetMapping(value = {"", "/list"})
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses(
            @RequestParam(value = "category", required = false) Long category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CourseResponse> coursePage;

            if (category == null) {
                coursePage = courseService.findAll(pageable);
            } else {
                coursePage = courseService.findByCategory(category, pageable);
            }

            if (coursePage.isEmpty()) {
                throw new NotFoundException("Course not found.");
            }

            MetadataResponse metadata = new MetadataResponse(
                    coursePage.getTotalElements(),
                    coursePage.getTotalPages(),
                    coursePage.getNumber(),
                    coursePage.getSize(),
                    (coursePage.hasNext() ? "/api/courses/list?page=" + (coursePage.getNumber() + 1) : null),
                    (coursePage.hasPrevious() ? "/api/courses/list?page=" + (coursePage.getNumber() - 1) : null),
                    "/api/courses/list?page=" + (coursePage.getTotalPages() - 1),
                    "/api/courses/list?page=0"
            );

            ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            Map<String, Object> responseMetadata = new HashMap<>();
            responseMetadata.put("pagination", metadata);
            apiResponse.ok(coursePage.getContent(), responseMetadata);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_INSTRUCTOR') and authentication.principal.getId() == #courseRequest.instructorId)")
    @PutMapping(value = "/updateCourse")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@Valid @RequestBody CourseRequest courseRequest,
            BindingResult bindingResult) {
        try {
//            CourseResponse courseResponse = courseService.update(courseRequest, bindingResult);
            ApiResponse apiResponse = new ApiResponse<>();
//            apiResponse.ok(courseResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
            // other exception throw application() (not exception of user)
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_INSTRUCTOR') and authentication.principal.getId() == #courseCreateRequest.instructorId)")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
            @ModelAttribute @Valid @RequestBody CourseCreateRequest courseCreateRequest) {
        CourseResponse courseResponse = courseService.saveCourse(courseCreateRequest);
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.ok(courseResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_INSTRUCTOR') " +
            "and @courseRepository.existsByInstructor_UserIdAndCourseId(authentication.principal.getId(), #id))")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> deleteCourse(@Valid @PathVariable("id") Long id) {
        System.err.println("deleteCourse");
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.ok(courseService.deleteCourse(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_INSTRUCTOR') " +
            "and @courseRepository.existsByInstructor_UserIdAndCourseId(authentication.principal.getId(), #sectionRequest.courseId))")
    @PostMapping(value = "/addSection")
    public ResponseEntity<ApiResponse<SectionCreateResponse>> createSection(
            @RequestBody SectionRequest sectionRequest) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.ok(courseService.createSection(sectionRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_INSTRUCTOR') " +
            "and @sectionRepository.existsByCourse_Instructor_UserIdAndSectionId(authentication.principal.getId(), #contentCreateRequest.sectionId))")
    @PostMapping(value = "/addContent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ContentCreateResponse>> createContent(
            @ModelAttribute @Valid ContentCreateRequest contentCreateRequest) {
        ContentCreateResponse contentCreateResponse = courseService
                .saveContent(contentCreateRequest);
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.ok(contentCreateResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);

    }

}
