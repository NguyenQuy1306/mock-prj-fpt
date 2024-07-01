package com.curcus.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.StudentResponse;
import com.curcus.lms.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

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

}