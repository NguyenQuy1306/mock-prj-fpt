package com.curcus.lms.controller;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Enrollment;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.request.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.StudentResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.EnrollmentResponse;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.EnrollmentRepository;
import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.service.StudentService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping(value = { "", "/list" })
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        try {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(studentService.findAll());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

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
            error.put("message", "An error occurred while processing the request");
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(@RequestBody @Valid StudentRequest studentRequest) {
        try {
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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentRequest studentRequest) {
        try {
            StudentResponse studentResponse = studentService.updateStudent(id, studentRequest);
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(studentResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while updating the student");
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/changePassword")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudentPassword(@PathVariable Long id, @RequestBody @Valid StudentRequest studentRequest) {
        try {
            StudentResponse studentResponse = studentService.updateStudentPassword(id, studentRequest);
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(studentResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while updating the student password");
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.ok();
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while deleting the student");
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}/courses")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getCoursesByStudentId(@PathVariable Long id){
        try{
            // List<Enrollment> enrollments = enrollmentRepository.findByStudent_UserId(id);
            // ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            // List<CourseResponse> courseResponses = enrollments.stream().map(enrollment -> {return courseMapper.toResponse(enrollment.getCourse());}).collect(Collectors.toList());
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
            error.put("message", "An error occurred while get list course");
            ApiResponse<List<EnrollmentResponse>> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/{studentId}/courses/{courseId}")
//    public Student studentEnrollCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
//
//    }

}