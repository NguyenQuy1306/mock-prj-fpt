package com.curcus.lms.service.impl;

import java.io.Console;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Content;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Section;

import com.curcus.lms.model.mapper.ContentMapper;
import com.curcus.lms.model.mapper.CourseMapper;

import com.curcus.lms.model.request.ContentCreateRequest;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.request.SectionRequest;

import com.curcus.lms.model.response.ContentCreateResponse;
import com.curcus.lms.model.response.CourseResponse;

import com.curcus.lms.repository.CategoryRepository;
import com.curcus.lms.repository.ContentRepository;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.InstructorRepository;
import com.curcus.lms.repository.SectionRepository;

import com.curcus.lms.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ContentMapper contentMapper;

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

    // @Override
    // public CourseResponse saveCourse(CourseCreateRequest courseCreateRequest) {
    // // TODO Auto-generated method stub
    // Instructor instructor =
    // instructorRepository.findById(courseCreateRequest.getInstructorId())
    // .orElseThrow(() -> new NotFoundException(
    // "Instructor has not existed with id" +
    // courseCreateRequest.getInstructorId()));
    // Category category =
    // categoryRepository.findById(courseCreateRequest.getCategoryId())
    // .orElseThrow(() -> new NotFoundException(
    // "Category has not existed with id " + courseCreateRequest.getCategoryId()));

    // Course course = courseMapper.toEntity(courseCreateRequest);
    // System.out.println(course.toString());
    // Course savedCourse = courseRepository.save(course);
    // return courseMapper.toResponse(savedCourse);
    // }

    @Override
    public CourseResponse deleteCourse(Long id) {
        // TODO Auto-generated method stub
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Course has not existed with id"));
        if (!course.getEnrollment().isEmpty())
            throw new ValidationException("The course cannot be deleted because someone is currently enrolled");
        courseRepository.deleteById(id);

        return courseMapper.toResponse(course);
    }

    @Override
    public CourseResponse saveCourse(CourseCreateRequest courseCreateRequest) {
        // TODO Auto-generated method stub
        Course course = courseMapper.toEntity(courseCreateRequest);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponse(savedCourse);
    }

    @Override
    public ContentCreateResponse saveContent(ContentCreateRequest contentCreateRequest) {
        // TODO Auto-generated method stub
    	System.out.println("contentCreateRequest is "+contentCreateRequest);
        Content content = contentMapper.toEntity(contentCreateRequest);
        System.out.println(content.toString());
        content = contentRepository.save(content);
        return contentMapper.toResponse(content);
    }

    @Override
    public Section createSection(SectionRequest sectionRequest) {
        Section section = new Section();
        Course course = courseRepository.findById(sectionRequest.getCourseId())
                .orElseThrow(() -> new NotFoundException(
                        "Course has not existed with id"));

        section.setCourse(course);
        section.setSectionName(sectionRequest.getSectionName());
        return sectionRepository.save(section);
    }
}
