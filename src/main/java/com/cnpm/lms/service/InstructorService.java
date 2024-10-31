package com.cnpm.lms.service;

import java.util.List;
import java.util.Optional;

import com.cnpm.lms.model.entity.Instructor;
import com.cnpm.lms.model.request.InstructorRequest;
import com.cnpm.lms.model.request.InstructorUpdateRequest;
import com.cnpm.lms.model.request.UserAddressRequest;
import com.cnpm.lms.model.response.InstructorGetCourseResponse;
import com.cnpm.lms.model.response.InstructorResponse;
import com.cnpm.lms.model.response.UserAddressResponse;

public interface InstructorService {
    List<InstructorResponse> findAll();

    List<InstructorResponse> findByName(String name);

    Optional<InstructorResponse> findById(Long instructorId);

    InstructorResponse createInstructor(InstructorRequest instructorRequest);

    InstructorResponse updateInstructor(InstructorUpdateRequest instructorUpdateRequest, Long id);

    InstructorResponse updateInstructorPassword(Long id, String password);

    InstructorResponse recoverInstructorPassword(Long id, String password);

    void deleteInstructor(Long instructorId);

    // List<InstructorGetCourseResponse> getCoursesByInstructor(Long instructorId);

    UserAddressResponse updateInstructorAddress(Long userId, UserAddressRequest addressRequest);
}
