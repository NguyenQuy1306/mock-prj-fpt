package com.curcus.lms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.repository.InstructorRepository;
import com.curcus.lms.service.InstructorService;

@Service

public class InstructorServiceImpl implements InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public Instructor findById(Long id) {
        try {
            System.out.println("instructorinstructorinstructor+ ");
            Instructor instructor = instructorRepository.findById(id).orElse(null);
            return instructor;
        } catch (Exception ex) {
            throw new ApplicationException();
        }

    }
}
