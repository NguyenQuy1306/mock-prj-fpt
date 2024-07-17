package com.curcus.lms.model.mapper;

import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.response.SectionCreateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SectionMapper {
	@Mapping(source = "course.courseId", target = "courseId")
    SectionCreateResponse toResponse(Section section);
}