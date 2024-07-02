package com.curcus.lms.service;

import com.curcus.lms.model.request.StudentRequest;
import com.curcus.lms.model.response.StudentResponse;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentResponse> findAll();

    Optional<StudentResponse> findById(Long studentId);

    StudentResponse saveStudent(StudentRequest student);

    void deleteStudent(Long studentId);

}
