package com.curcus.lms.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.repository.CategoryRepository;
import com.curcus.lms.repository.InstructorRepository;
import com.curcus.lms.repository.UserRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CourseMapper {
	@Autowired
    protected InstructorRepository instructorRepository;

    @Autowired
    protected CategoryRepository categoryRepository;
    
	@Mapping(source = "course.instructor.userId", target = "instructorId")
	@Mapping(source = "course.category.categoryId", target = "categoryId")
	
	public abstract CourseResponse toResponse(Course course);
	public abstract List<CourseResponse> toResponseList(List<Course> courses);

	@Mapping(target = "instructor", expression = "java(findInstructorById(courseCreateRequest.getInstructorId()))")
    @Mapping(target = "category", expression = "java(findCategoryById(courseCreateRequest.getCategoryId()))")
    public abstract Course toEntity(CourseCreateRequest courseCreateRequest);
	protected Instructor findInstructorById(Long id) {
        return instructorRepository.findById(id).orElseThrow(
        		()-> new NotFoundException("Instructor has not existed with id "+id));
    }

    protected Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
        		()-> new NotFoundException("Category has not existed with id "+id));
    }
}
