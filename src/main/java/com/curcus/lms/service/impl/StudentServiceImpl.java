package com.curcus.lms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.service.StudentService;
import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.mapper.UserMapper;
import com.curcus.lms.model.response.StudentResponse;

import java.util.List;

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
}
