package com.curcus.lms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.repository.CategoryRepository;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.InstructorRepository;
import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.service.CourseService;
import com.curcus.lms.exception.NotFoundException;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseResponse> findAll() {
        return courseMapper.toResponseList(courseRepository.findAll());
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
    public CourseResponse saveCourse(CourseCreateRequest courseCreateRequest) {
        // TODO Auto-generated method stub
        Instructor instructor = instructorRepository.findById(courseCreateRequest.getInstructorId())
                .orElseThrow(() -> new NotFoundException(
                        "Instructor has not existed with id" + courseCreateRequest.getInstructorId()));
        Category category = categoryRepository.findById(courseCreateRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException(
                        "Category has not existed with id " + courseCreateRequest.getCategoryId()));

        Course course = courseMapper.toEntity(courseCreateRequest);
        System.out.println(course.toString());
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponse(savedCourse);
    }

    @Override
    public CourseResponse deleteCourse(Long id) {
        // TODO Auto-generated method stub
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Course has not existed with id"));
        courseRepository.deleteById(id);

        return courseMapper.toResponse(course);
    }
}
