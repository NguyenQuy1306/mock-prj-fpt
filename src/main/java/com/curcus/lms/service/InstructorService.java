package com.curcus.lms.service;

import com.curcus.lms.model.dto.InstructorDTO;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.request.InstructorCreateRequest;
import com.curcus.lms.repository.InstructorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public interface InstructorService {
    InstructorDTO createInstructor(InstructorCreateRequest request);
    InstructorDTO getInstructor(Long id);
    List<InstructorDTO> getAllInstructors();
    InstructorDTO updateInstructor(Long id, InstructorCreateRequest request);
    void deleteInstructor(Long id);
}