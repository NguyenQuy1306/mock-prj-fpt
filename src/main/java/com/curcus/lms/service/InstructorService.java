package com.curcus.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.curcus.lms.model.entity.Instructor;

@Service
public interface InstructorService {
    public List<Instructor> findAll();
    
    public Optional<Instructor> getInstructorById(Long id);

    public Instructor createInstructor(Instructor instructor);

    public Instructor updateInstructor(Instructor instructor);

    public void deleteInstructor(Long id);
} 
