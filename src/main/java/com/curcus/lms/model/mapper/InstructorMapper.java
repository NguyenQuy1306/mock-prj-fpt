package com.curcus.lms.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.response.InstructorResponse;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    @Mapping(source = "userId", target = "instructorId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    InstructorResponse toResponse(Instructor instructor);
    
    //List<InstructorResponse> toInstructorResponseList(List<Instructor> instructors);

    
     //Instructor toInstructorEnity(InstructorRequest request);
    
}
