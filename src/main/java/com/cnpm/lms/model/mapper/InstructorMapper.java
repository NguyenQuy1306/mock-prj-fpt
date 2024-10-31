package com.cnpm.lms.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.cnpm.lms.model.entity.Instructor;
import com.cnpm.lms.model.entity.User;
import com.cnpm.lms.model.response.InstructorPublicResponse;
import com.cnpm.lms.model.response.InstructorResponse;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    @Mapping(source = "userId", target = "instructorId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    InstructorResponse toResponse(Instructor instructor);

    @Mapping(source = "userId", target = "instructorId")
    @Named("toDetailResponse")
    InstructorResponse toDetailResponse(User instructor);

    @Mapping(source = "avtUrl", target = "avtUrl")
    @Mapping(source = "name", target = "name")
    InstructorPublicResponse toInstructorPublicResponse(Instructor instructor);
}
