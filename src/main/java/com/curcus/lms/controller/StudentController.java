package com.curcus.lms.controller;

import com.curcus.lms.model.request.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.StudentResponse;
import com.curcus.lms.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

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
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(@RequestBody StudentRequest studentRequest) {
        return saveOrUpdateStudent(studentRequest, false);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        return saveOrUpdateStudent(studentRequest, true);
    }

    private ResponseEntity<ApiResponse<StudentResponse>> saveOrUpdateStudent(StudentRequest studentRequest, boolean isUpdate) {
        try {
            StudentResponse studentResponse = studentService.saveStudent(studentRequest);
            ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(studentResponse);
            return new ResponseEntity<>(apiResponse, isUpdate ? HttpStatus.OK : HttpStatus.CREATED);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while " + (isUpdate ? "updating" : "saving") + " the student");
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
//    @GetMapping("/{id}/courses")
//    public List<Course> getCoursesByStudentId(@PathVariable Long id){
//    }
//
//    @PostMapping("/{studentId}/courses/{courseId}")
//    public Student studentEnrollCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
//
//    }

}