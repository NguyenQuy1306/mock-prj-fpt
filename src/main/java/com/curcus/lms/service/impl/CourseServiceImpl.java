package com.curcus.lms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
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
}
