package com.curcus.lms.service;

import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.response.StudentResponse;
import com.curcus.lms.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    List<StudentResponse> findAll();
    
    public List<Student> getAllStudents();

    public Optional<Student> getStudentById(Long id);

    public Student saveStudent(Student student);

    public void deleteStudent(Long id);
}
