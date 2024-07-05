package com.curcus.lms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.service.StudentService;
import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.mapper.UserMapper;
import com.curcus.lms.model.request.StudentRequest;
import com.curcus.lms.model.response.StudentResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<StudentResponse> findAll() {
        try {
            return userMapper.toResponseList(studentRepository.findAll());
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public Optional<StudentResponse> findById(Long studentId) {
        try {
            return studentRepository.findById(studentId).map(userMapper::toResponse);
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) {
        try {
            if (studentRepository.existsByEmail(studentRequest.getEmail())) throw new ApplicationException("Email đã tồn tại");
            if (studentRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) throw new ApplicationException("PhoneNumber đã tồn tại");
            Student newStudent = new Student();
            newStudent.setName(studentRequest.getName());
            newStudent.setEmail(studentRequest.getEmail());
            newStudent.setPassword(studentRequest.getPassword());
            newStudent.setFirstName(studentRequest.getFirstName());
            newStudent.setLastName(studentRequest.getLastName());
            newStudent.setPhoneNumber(studentRequest.getPhoneNumber());
            return userMapper.toResponse(studentRepository.save(newStudent));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public StudentResponse updateStudent(Long studentId, StudentRequest studentRequest) {
        try {
            if (!studentRepository.existsById(studentId)) throw new ApplicationException("Tài khoản không tồn tại");
            Student newStudent = studentRepository.findById(studentId).orElse(null);
            newStudent.setName(studentRequest.getName());

            if(!newStudent.getEmail().equals(studentRequest.getEmail())) {
                if (studentRepository.existsByEmail(studentRequest.getEmail())) throw new ApplicationException("Email đã tồn tại");
            }
            newStudent.setEmail(studentRequest.getEmail());
            newStudent.setPassword(studentRequest.getPassword());
            newStudent.setFirstName(studentRequest.getFirstName());
            newStudent.setLastName(studentRequest.getLastName());

            if(!newStudent.getPhoneNumber().equals(studentRequest.getPhoneNumber())) {
                if (studentRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) throw new ApplicationException("PhoneNumber đã tồn tại");
            }
            newStudent.setPhoneNumber(studentRequest.getPhoneNumber());
            return userMapper.toResponse(studentRepository.save(newStudent));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public void deleteStudent(Long studentId) {
        try {
            if (!studentRepository.existsById(studentId)) throw new ApplicationException("Tài khoản không tồn tại");
            studentRepository.deleteById(studentId);
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

}
