package com.curcus.lms.controller;

import com.curcus.lms.model.request.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.StudentResponse;
import com.curcus.lms.model.response.EnrollmentResponse;
import com.curcus.lms.service.StudentService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = { "", "/list" })
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        try {
            ApiResponse<List<StudentResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(studentService.findAll());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_STUDENT') and authentication.principal.getId() == #id)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        try {
            Optional<StudentResponse> studentResponse = studentService.findById(id);
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            if (studentResponse.isPresent()) {
                apiResponse.ok(studentResponse.get());
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Student not found");
                apiResponse.error(error);
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(@RequestBody @Valid StudentRequest studentRequest,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                throw new Exception("Request is not valid");
            StudentResponse studentResponse = studentService.createStudent(studentRequest);
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(studentResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while creating the student");
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_STUDENT') and authentication.principal.getId() == #id)")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(@PathVariable Long id,
            @RequestBody @Valid StudentRequest studentRequest, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                throw new Exception("Request is not valid");
            StudentResponse studentResponse = studentService.updateStudent(id, studentRequest);
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(studentResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_STUDENT') and authentication.principal.getId() == #id)")
    @PutMapping("/{id}/changePassword")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudentPassword(@PathVariable Long id,
            @RequestBody StudentRequest studentRequest) {
        try {
            StudentResponse studentResponse = studentService.updateStudentPassword(id, studentRequest);
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(studentResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.ok();
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_STUDENT') and authentication.principal.getId() == #id)")
    @GetMapping("/{id}/courses")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getCoursesByStudentId(@PathVariable Long id) {
        try {
            // List<Enrollment> enrollments = enrollmentRepository.findByStudent_UserId(id);
            // ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            // List<CourseResponse> courseResponses = enrollments.stream().map(enrollment ->
            // {return
            // courseMapper.toResponse(enrollment.getCourse());}).collect(Collectors.toList());
            // apiResponse.ok(courseResponses);
            // return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            List<EnrollmentResponse> enrollments = studentService.getCoursesByStudentId(id);
            ApiResponse<List<EnrollmentResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(enrollments);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception ex) {
            // Map<String, String> error = new HashMap<>();
            // error.put("message", "An error occurred while get list course");
            // ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            // apiResponse.error(error);
            // return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<List<EnrollmentResponse>> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_STUDENT') and authentication.principal.getId() == #id")
    @GetMapping("/{id}/cart")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getListCourseFromCart(@PathVariable Long id) {
        try {
            List<CourseResponse> listCourse = studentService.getListCourseFromCart(id);
            ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(listCourse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_STUDENT') and authentication.principal.getId() == #studentId)")
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> addStudentToCourse(@PathVariable Long studentId,
            @PathVariable Long courseId) {
        try {
            EnrollmentResponse enrollmentResponse = studentService.addStudentToCourse(studentId, courseId);
            ApiResponse<EnrollmentResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(enrollmentResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<EnrollmentResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_STUDENT') and authentication.principal.getId() == #studentId)")
    @PostMapping("/{studentId}/enrollFromCart")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> addStudentToCoursesFromCart(
            @PathVariable Long studentId) {
        try {
            List<EnrollmentResponse> enrollmentResponses = studentService.addStudentToCoursesFromCart(studentId);
            ApiResponse<List<EnrollmentResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(enrollmentResponses);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<List<EnrollmentResponse>> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}