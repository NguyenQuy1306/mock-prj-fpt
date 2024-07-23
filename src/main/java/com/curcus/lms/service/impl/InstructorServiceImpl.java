package com.curcus.lms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.entity.Enrollment;
import com.curcus.lms.model.response.InstructorGetCourseResponse;
import com.curcus.lms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.mapper.UserMapper;
import com.curcus.lms.model.request.InstructorRequest;
import com.curcus.lms.model.request.InstructorUpdateRequest;
import com.curcus.lms.model.response.InstructorResponse;
import com.curcus.lms.repository.InstructorRepository;
import com.curcus.lms.service.InstructorService;

import jakarta.validation.ValidationException;

@Service
public class InstructorServiceImpl implements InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<InstructorResponse> findAll() {
        try {
            return userMapper.toInstructorResponseList(instructorRepository.findAll());
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<InstructorResponse> findByName(String name) {
        try {
            return userMapper.toInstructorResponseList(instructorRepository.findByName(name));
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public Optional<InstructorResponse> findById(Long instructorId) {
        try {
            return instructorRepository.findById(instructorId).map(userMapper::toInstructorResponse);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public InstructorResponse createInstructor(InstructorRequest instructorRequest) {
        try {
            Instructor newInstructor = new Instructor();
            newInstructor.setName(instructorRequest.getName());
            if (instructorRepository.findByEmail(instructorRequest.getEmail()) != null)
                throw new ValidationException("Email already exists");
            newInstructor.setEmail(instructorRequest.getEmail());
            newInstructor.setPassword(instructorRequest.getPassword());
            newInstructor.setFirstName(instructorRequest.getFirstName());
            newInstructor.setLastName(instructorRequest.getLastName());
            if (instructorRepository.findByEmail(instructorRequest.getPhoneNumber()) != null)
                throw new ApplicationException("PhoneNumber already exists");
            newInstructor.setPhoneNumber(instructorRequest.getPhoneNumber());
            return userMapper.toInstructorResponse(instructorRepository.save(newInstructor));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public InstructorResponse updateInstructor(InstructorUpdateRequest instructorUpdateRequest, Long id) {
        try {
            if (instructorRepository.findById(id) == null)
                throw new ApplicationException("Unknown account");
            Instructor newInstructor = instructorRepository.findById(id).get();
            if (instructorUpdateRequest.getName() != null)
                newInstructor.setName(instructorUpdateRequest.getName());
            if (instructorUpdateRequest.getFirstName() != null)
                newInstructor.setFirstName(instructorUpdateRequest.getFirstName());
            if (instructorUpdateRequest.getLastName() != null)
                newInstructor.setLastName(instructorUpdateRequest.getLastName());
            if (instructorUpdateRequest.getPhoneNumber() != null) {
                if (instructorRepository.findByEmail(instructorUpdateRequest.getPhoneNumber()) != null)
                    throw new ApplicationException("PhoneNumber already exists");
                newInstructor.setPhoneNumber(instructorUpdateRequest.getPhoneNumber());
            }
            return userMapper.toInstructorResponse(instructorRepository.save(newInstructor));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public InstructorResponse updateInstructorPassword(Long id, String password) {
        try {
            if (instructorRepository.findById(id) == null)
                throw new ApplicationException("Unknown account");
            Instructor newInstructor = instructorRepository.findById(id).get();
            if (newInstructor.getPassword().equals(password))
                throw new ApplicationException("Password already exists in your account");
            newInstructor.setPassword(password);
            return userMapper.toInstructorResponse(instructorRepository.save(newInstructor));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public InstructorResponse recoverInstructorPassword(Long id, String password) {
        try {
            if (instructorRepository.findById(id) == null)
                throw new ApplicationException("Unknown account");
            Instructor newInstructor = instructorRepository.findById(id).get();
            newInstructor.setPassword(password);
            return userMapper.toInstructorResponse(instructorRepository.save(newInstructor));
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void deleteInstructor(Long instructorId) {
        try {
            System.out.println(123);
            if (instructorRepository.findById(instructorId).isPresent())
                instructorRepository.deleteById(instructorId);
            else
                throw new NotFoundException("Unknown account");
            System.out.println(9999);

        } catch (ApplicationException ex) {
            throw ex;
        }

    }


    @Override
    public List<InstructorGetCourseResponse> getCoursesByInstructor(Long instructorId) {
        List<Course> courses = courseRepository.findAllByInstructorId(instructorId);
        List<InstructorGetCourseResponse> responseList = new ArrayList<>();
        for (Course course : courses) {
            List<Student> studentList = course.getEnrollment().stream()
                    .map(Enrollment::getStudent)
                    .collect(Collectors.toList());

            InstructorGetCourseResponse response = new InstructorGetCourseResponse(
                    course.getCourseId(),
                    course.getCourseThumbnail(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getPrice(),
                    course.getCategory().getCategoryId(),
                    studentList,
                    course.getCreatedAt(),
                    "Approved" // Assuming default status
            );

            responseList.add(response);
        }

        return responseList;
    }
}