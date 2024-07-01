package com.curcus.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.StudentResponse;
import com.curcus.lms.service.StudentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping(value = { "", "/list" })
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(studentService.findAll());
        System.out.println("day la result " + studentService.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable Long id) {
    }

    @PostMapping
    public Student saveStudent(@RequestBody Student student) {
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
    }

    @GetMapping("/{id}/courses")
    public List<Course> getCoursesByStudentId(@PathVariable Long id){
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public Student studentEnrollCourse(@PathVariable Long studentId, @PathVariable Long courseId) {

    }

}