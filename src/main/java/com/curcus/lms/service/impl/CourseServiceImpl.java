package com.curcus.lms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.InstructorRepository;
import com.curcus.lms.service.CategoryService;
import com.curcus.lms.service.CourseService;
import com.curcus.lms.service.InstructorService;
import com.curcus.lms.util.ValidatorUtil;
import com.curcus.lms.validation.CourseValidator;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseValidator courseValidator;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ValidatorUtil validatorUtil;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public List<CourseResponse> findAll() {
        return courseMapper.toResponseList(courseRepository.findAll());
    }

    @Override
    public Course findById(Long id) {
        try {
            return courseRepository.findById(id).orElse(null);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @Override
    public Instructor findByIdInstructor(Long id) {
        try {
            return instructorRepository.findById(id).orElse(null);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @Override
    public List<CourseResponse> findByCategory(int categoryId) {
        try {
            Category category = new Category();
            category.setCategoryId(categoryId);
            return courseMapper.toResponseList(courseRepository.findByCategory(category));
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public void checkCourseRequest(CourseRequest courseRequest, BindingResult bindingResult) {
        // Get id of course
        if (findById(courseRequest.getCourseId()) == null) {
            throw new NotFoundException("Course not found.");
        }
        // Validator to check category and instructor of course
        courseValidator.validate(courseRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new NotFoundException(bindingResult.getFieldError().getDefaultMessage());
        }

    }

    @Override
    public CourseResponse update(CourseRequest courseRequest, BindingResult bindingResult) {
        try {
            // call method check course
            checkCourseRequest(courseRequest, bindingResult);
            // set category entity to course
            Course course = courseMapper.toRequest(courseRequest);
            Category category = categoryService.findById(courseRequest.getCategoryId());
            course.setCategory(category);
            // set instructor entity to course
            Instructor instructor = instructorService.findById(courseRequest.getInstructorId());
            course.setInstructor(instructor);
            // Save update course
            courseRepository.save(course);
            // Mapping course to courseResponse
            CourseResponse courseResponse = courseMapper.toResponse(course);
            return courseResponse;
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

}
