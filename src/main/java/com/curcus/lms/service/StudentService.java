package com.curcus.lms.service;

import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> findAll();

    Student findById(Long Id);
}
