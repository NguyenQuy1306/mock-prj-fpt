package com.curcus.lms.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.response.CourseResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "instructor.userId", target = "instructorId")
    @Mapping(source = "instructor", target = "instructor")
    @Mapping(source = "category.categoryId", target = "categoryId")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "enrollment", target = "enrollment")
    CourseResponse toResponse(Course course);

    List<CourseResponse> toResponseList(List<Course> courses);
}
