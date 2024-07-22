package com.curcus.lms.model.mapper;

import com.curcus.lms.model.entity.Admin;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.response.*;
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

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    UserResponse toUserResponse(Student student);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    UserResponse toUserResponse(Instructor instructor);


    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "userAddress", target = "userAddress")
    @Mapping(source = "userCity", target = "userCity")
    @Mapping(source = "userCountry", target = "userCountry")
    @Mapping(source = "userPostalCode", target = "userPostalCode")
    UserAddressResponse toUserAddressResponse(Student student);


    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "userAddress", target = "userAddress")
    @Mapping(source = "userCity", target = "userCity")
    @Mapping(source = "userCountry", target = "userCountry")
    @Mapping(source = "userPostalCode", target = "userPostalCode")
    UserAddressResponse toUserAddressResponse(Instructor instructor);

}
