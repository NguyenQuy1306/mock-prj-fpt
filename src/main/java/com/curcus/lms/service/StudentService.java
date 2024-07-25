package com.curcus.lms.service;

import com.curcus.lms.model.request.StudentRequest;
import com.curcus.lms.model.request.UserAddressRequest;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.EnrollmentResponse;
import com.curcus.lms.model.response.StudentStatisticResponse;
import com.curcus.lms.model.response.StudentResponse;
import com.curcus.lms.model.response.UserAddressResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentResponse> findAll();

    Optional<StudentResponse> findById(Long studentId);
    
    StudentResponse createStudent(StudentRequest student);

    StudentResponse updateStudent(Long studentId, StudentRequest student);

    StudentResponse updateStudentPassword(Long studentId, StudentRequest student);

    void deleteStudent(Long studentId);

    List<EnrollmentResponse> getCoursesByStudentId(Long studentId);

    List<CourseResponse> getListCourseFromCart(Long studentId);

    EnrollmentResponse addStudentToCourse(Long studentId, Long courseId);

    List<EnrollmentResponse> addStudentToCoursesFromCart(Long studentId);

    HashMap<String, Integer> getCoursesPurchasedLastFiveYears(Long studentId);

    Integer getTotalPurchaseCourse(Long studentId);

    Integer totalFinishCourse(Long studentId);

    HashMap<String, Integer> finishCourseFiveYears(Long studentId);

    StudentStatisticResponse studentStatistic(Long studentId);


    UserAddressResponse updateStudentAddress(Long userId, UserAddressRequest addressRequest);
}
