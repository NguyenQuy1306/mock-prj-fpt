package com.curcus.lms.service;

import java.util.List;
import java.util.Optional;

import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.request.InstructorRequest;
import com.curcus.lms.model.response.InstructorResponse;

public interface InstructorService {
    List<InstructorResponse> findAll();
    
    List<InstructorResponse> findByName(String name);

    Optional<InstructorResponse> findById(Long instructorId);

    InstructorResponse saveInstructor(InstructorRequest Instructor);

    InstructorResponse newUpdateInstructor(InstructorRequest Instructor, Long id);

    void deleteInstructor(Long instructorId);

}
