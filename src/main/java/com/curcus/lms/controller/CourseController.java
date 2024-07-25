package com.curcus.lms.controller;

import com.curcus.lms.model.request.*;
import com.curcus.lms.model.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curcus.lms.model.response.MetadataResponse;
import com.curcus.lms.constants.CourseSearchOptions;
import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.SearchOptionsException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.service.CourseService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PutMapping;

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
            // CourseResponse courseResponse = courseService.update(courseRequest,
            // bindingResult);
            ApiResponse apiResponse = new ApiResponse<>();
            // apiResponse.ok(courseResponse);
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

    @GetMapping("courses/search")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> searchCourses(
            @RequestParam(required = false) Long instructorId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String direction // parameter for sorting direction
    ) {
    	if (sort != null && !CourseSearchOptions.SORT_OPTIONS.contains(sort)) {
            throw new SearchOptionsException( "Invalid sort parameter.");
        }

        if (direction != null && !CourseSearchOptions.DIRECTION_OPTIONS.contains(direction.toLowerCase())) {
        	throw new SearchOptionsException(  "Invalid direction parameter.");
        }
        // Validate direction parameter
        Sort.Direction sortDirection = Sort.Direction.ASC; // default to ascending
        if (direction != null && direction.equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }

        // Build pageable with sorting
        Pageable pageable;
        if (sort != null && !sort.isEmpty()) {
            pageable = PageRequest.of(page, size, sortDirection, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }

        // Call service method to search courses
        Page<CourseResponse> coursePage = courseService.searchCourses(instructorId, categoryId, title, minPrice, pageable);

        // Handle empty result
        if (coursePage.isEmpty()) {
            throw new NotFoundException("Course not found.");
        }

        // Create metadata for pagination
        MetadataResponse metadata = new MetadataResponse(
                coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                coursePage.getNumber(),
                coursePage.getSize(),
                (coursePage.hasNext() ? "/api/courses/courses/search?page=" + (coursePage.getNumber() + 1) : null),
                (coursePage.hasPrevious() ? "/api/courses/courses/search?page=" + (coursePage.getNumber() - 1) : null),
                "/api/courses/courses/search?page=" + (coursePage.getTotalPages() - 1),
                "/api/courses/courses/search?page=0"
        );

        // Create API response
        ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
        Map<String, Object> responseMetadata = new HashMap<>();

        responseMetadata.put("pagination", metadata);
        responseMetadata.put("searchOptions", CourseSearchOptions.HINTS_MAP);

        apiResponse.ok(coursePage.getContent(), responseMetadata);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_INSTRUCTOR') " +
            "and @courseRepository.existsByInstructor_UserIdAndCourseId(authentication.principal.getId(), #courseStatusRequest.courseId)" +
            // instructor chỉ được đổi status sang CREATED hoặc PENDING_APPROVAL chứ ko được đổi sang APPROVED hay REJECTED
            "and (#courseStatusRequest.status == 'CREATED' or #courseStatusRequest.status == 'PENDING_APPROVAL'))")
    @PutMapping("/update-course-status")
    public ResponseEntity<ApiResponse<CourseStatusResponse>> updateCourseStatus(@Valid @RequestBody CourseStatusRequest courseStatusRequest,
                                                                                BindingResult bindingResult) {
        ApiResponse<CourseStatusResponse> apiResponse = new ApiResponse<>();
        try {
            if (bindingResult.hasErrors()) {
                apiResponse.error(ResponseCode.getError(1));
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }

            apiResponse.ok(courseService.updateCourseStatus(courseStatusRequest.getCourseId(), courseStatusRequest.getStatus()));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch(NotFoundException e) {
            apiResponse.error(ResponseCode.getError(10));
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        } catch(ValidationException e) {
            apiResponse.error(ResponseCode.getError(1));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            apiResponse.error(ResponseCode.getError(23));
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
