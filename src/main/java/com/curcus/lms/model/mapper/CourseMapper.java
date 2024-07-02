package com.curcus.lms.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.response.CourseResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {


    CourseResponse toResponse(Course course);

    List<CourseResponse> toResponseList(List<Course> courses);
}
