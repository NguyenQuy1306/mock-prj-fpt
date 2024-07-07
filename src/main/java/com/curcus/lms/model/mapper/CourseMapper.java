package com.curcus.lms.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.repository.CategoryRepository;
import com.curcus.lms.repository.UserRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CourseMapper {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Mapping(source = "course.instructor.userId", target = "instructorId")
    @Mapping(source = "course.category.categoryId", target = "categoryId")

    public abstract CourseResponse toResponse(Course course);

    public abstract List<CourseResponse> toResponseList(List<Course> courses);

    @Mapping(target = "instructor", expression = "java(findUserById(courseCreateRequest.getInstructorId()))")
    @Mapping(target = "category", expression = "java(findCategoryById(courseCreateRequest.getCategoryId()))")
    public abstract Course toEntity(CourseCreateRequest courseCreateRequest);

    protected User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    protected Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
