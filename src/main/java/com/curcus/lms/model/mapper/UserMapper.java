package com.curcus.lms.model.mapper;

import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    Map Student Entity to StudentResponse
    @Mapping(source = "userId", target = "studentId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    StudentResponse toResponse(Student student);

    List<StudentResponse> toResponseList(List<Student> studentList);
}
