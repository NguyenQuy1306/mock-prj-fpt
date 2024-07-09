package com.curcus.lms.service;

import com.curcus.lms.model.entity.Enrollment;
import com.curcus.lms.model.request.StudentRequest;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.EnrollmentResponse;
import com.curcus.lms.model.response.StudentResponse;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface StudentService {
    List<StudentResponse> findAll();

    Optional<StudentResponse> findById(Long studentId);
    
    StudentResponse createStudent(StudentRequest student);

    StudentResponse updateStudent(Long studentId, StudentRequest student);

    StudentResponse updateStudentPassword(Long studentId, StudentRequest student);

    void deleteStudent(Long studentId);

    List<EnrollmentResponse> getCoursesByStudentId(Long studentId);

    EnrollmentResponse addStudentToCourse(Long studentId, Long courseId);
}
