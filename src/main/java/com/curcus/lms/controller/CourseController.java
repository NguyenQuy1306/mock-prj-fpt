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

<<<<<<< HEAD
=======
import org.springframework.validation.annotation.Validated;
>>>>>>> origin/merge
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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




import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PutMapping;

>>>>>>> origin/merge

@RestController
@RequestMapping("/api/courses")
@Validated
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
<<<<<<< HEAD
            List<CourseResponse> course = null;
=======
            Pageable pageable = PageRequest.of(page, size);
            Page<CourseResponse> coursePage;

>>>>>>> origin/merge
            if (category == null) {
                coursePage = courseService.findAll(pageable);
            } else {
                coursePage = courseService.findByCategory(category, pageable);
            }
<<<<<<< HEAD
            if (course.size() == 0) {
                throw new NotFoundException("Course not found");
=======

            if (coursePage.isEmpty()) {
                throw new NotFoundException("Course not found.");
>>>>>>> origin/merge
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

<<<<<<< HEAD
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@RequestParam Long courseId) {
        try {
            Course course = courseService.findById(courseId);
            if (course == null) {
                throw new NotFoundException("Course has not existed with id " + courseId);
            }
            CourseResponse courseResponse = courseMapper.toResponse(course);
            ApiResponse apiResponse = new ApiResponse<>();
            apiResponse.ok(courseResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @PutMapping(value = "/{course_id}/updateCourse")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@PathVariable Long course_id,
            @Valid @RequestBody CourseRequest courseRequest,
            BindingResult bindingResult) {
        try {
            if (course_id != courseRequest.getCourseId()) {
                throw new ValidationException("CourseId for parameter of api updateCourse is wrong");
            }
            CourseResponse courseResponse = courseService.update(courseRequest, bindingResult);
=======
    @PutMapping(value = "/updateCourse")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@Valid @RequestBody CourseRequest courseRequest,
            BindingResult bindingResult) {
        try {
//            CourseResponse courseResponse = courseService.update(courseRequest, bindingResult);
>>>>>>> origin/merge
            ApiResponse apiResponse = new ApiResponse<>();
//            apiResponse.ok(courseResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
<<<<<<< HEAD
=======
            // other exception throw application() (not exception of user)
>>>>>>> origin/merge
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
            @ModelAttribute @Valid @RequestBody CourseCreateRequest courseCreateRequest) {
        CourseResponse courseResponse = courseService.saveCourse(courseCreateRequest);
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.ok(courseResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> deleteCourse(@Valid @PathVariable("id") Long id) {
        System.err.println("deleteCourse");
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.ok(courseService.deleteCourse(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/addSection")
    public ResponseEntity<ApiResponse<SectionCreateResponse>> createSection(
            @RequestBody SectionRequest sectionRequest) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.ok(courseService.createSection(sectionRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/addContent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ContentCreateResponse>> createContent(
            @ModelAttribute @Valid ContentCreateRequest contentCreateRequest) {
        ContentCreateResponse contentCreateResponse = courseService
                .saveContent(contentCreateRequest);
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.ok(contentCreateResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

}
