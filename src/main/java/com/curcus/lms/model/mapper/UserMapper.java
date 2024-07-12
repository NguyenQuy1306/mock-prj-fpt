package com.curcus.lms.model.mapper;

import com.curcus.lms.model.entity.Admin;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.response.AdminResponse;
import com.curcus.lms.model.response.InstructorResponse;
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
    
    @Mapping(source = "userId", target = "instructorId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    InstructorResponse toInstructorResponse(Instructor instructor);

    List<InstructorResponse> toInstructorResponseList(List<Instructor> instructors);

    @Mapping(source = "userId", target = "adminId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    AdminResponse toAdminResponse(Admin admin);
}
