package com.curcus.lms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.curcus.lms.validation.InstructorValidator;

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
    private InstructorValidator instructorValidator;
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
    public Page<CourseResponse> findByCategory(Long categoryId, Pageable pageable) {
        try {
            Category category = new Category();
            category.setCategoryId(categoryId);
            Page<Course> coursesPage = courseRepository.findByCategory(category, pageable);
            return coursesPage.map(courseMapper::toResponse);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @Override
    public Page<CourseResponse> findAll(Pageable pageable) {
        try {
            Page<Course> coursesPage = courseRepository.findAll(pageable);
            return coursesPage.map(courseMapper::toResponse);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

//    @Override
//    public List<CourseResponse> findByCategory(Long categoryId) {
//        try {
//            Category category = new Category();
//            category.setCategoryId(categoryId);
//            return courseMapper.toResponseList(courseRepository.findByCategory(category));
//        } catch (ApplicationException ex) {
//            throw ex;
//        }
//    }

    @Override
    public CourseResponse update(CourseRequest courseRequest, BindingResult bindingResult) {
        // Get id of course
        if (findById(courseRequest.getCourseId()) == null) {
            throw new NotFoundException("Course not found.");
        }
        // Validator to check category of course
        courseValidator.validate(courseRequest, bindingResult);
        if (bindingResult.hasErrors()) {

            Map<String, String> validationException = validatorUtil.toErrors(bindingResult.getFieldErrors());
            throw new ValidationException(validationException);
        }
        // Get id of instructor
        if (findByIdInstructor(courseRequest.getInstructorId()) == null) {
            throw new NotFoundException("Instructor not found");
        }
        // Validator to check instructor of course
        instructorValidator.validate(courseRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, String> validationExceptionInstructor = validatorUtil.toErrors(bindingResult.getFieldErrors());
            throw new ValidationException(validationExceptionInstructor);
        }

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
    }

}
