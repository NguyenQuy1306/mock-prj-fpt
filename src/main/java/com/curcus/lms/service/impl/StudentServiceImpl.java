package com.curcus.lms.service.impl;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.repository.CartItemsRepository;
import com.curcus.lms.repository.CartRepository;
import com.curcus.lms.repository.EnrollmentRepository;
import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.service.StudentService;
import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.entity.Cart;
import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.entity.Enrollment;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.mapper.UserMapper;
import com.curcus.lms.model.request.StudentRequest;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.EnrollmentResponse;
import com.curcus.lms.model.response.StudentResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<StudentResponse> findAll() {
        try {
            return userMapper.toResponseList(studentRepository.findAll());
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public Optional<StudentResponse> findById(Long studentId) {
        try {
            return studentRepository.findById(studentId).map(userMapper::toResponse);
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) {
        try {
            if (studentRepository.existsByEmail(studentRequest.getEmail())) throw new ApplicationException("Email đã tồn tại");
            if (studentRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) throw new ApplicationException("PhoneNumber đã tồn tại");
            Student newStudent = new Student();
            newStudent.setName(studentRequest.getName());
            newStudent.setEmail(studentRequest.getEmail());
            newStudent.setPassword(studentRequest.getPassword());
            newStudent.setFirstName(studentRequest.getFirstName());
            newStudent.setLastName(studentRequest.getLastName());
            newStudent.setPhoneNumber(studentRequest.getPhoneNumber());
            return userMapper.toResponse(studentRepository.save(newStudent));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public StudentResponse updateStudent(Long studentId, StudentRequest studentRequest) {
        try {
            if (!studentRepository.existsById(studentId)) throw new ApplicationException("Tài khoản không tồn tại");
            Student newStudent = studentRepository.findById(studentId).orElse(null);
            newStudent.setName(studentRequest.getName());
            newStudent.setFirstName(studentRequest.getFirstName());
            newStudent.setLastName(studentRequest.getLastName());

            if(!newStudent.getPhoneNumber().equals(studentRequest.getPhoneNumber())) {
                if (studentRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) throw new ApplicationException("PhoneNumber đã tồn tại");
            }
            newStudent.setPhoneNumber(studentRequest.getPhoneNumber());
            return userMapper.toResponse(studentRepository.save(newStudent));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public StudentResponse updateStudentPassword(Long studentId, StudentRequest studentRequest) {
        try {
            if (!studentRepository.existsById(studentId)) throw new ApplicationException("Tài khoản không tồn tại");
            Student newStudent = studentRepository.findById(studentId).orElse(null);

            if(studentRequest.getPassword() == "") throw new ApplicationException("Please enter a password");
            if(newStudent.getPassword().equals(studentRequest.getPassword())) throw new ApplicationException("New password cannot be the same as the old password");
            newStudent.setPassword(studentRequest.getPassword());
            return userMapper.toResponse(studentRepository.save(newStudent));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public void deleteStudent(Long studentId) {
        try {
            if (!studentRepository.existsById(studentId)) throw new ApplicationException("Tài khoản không tồn tại");
            studentRepository.deleteById(studentId);
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public List<EnrollmentResponse> getCoursesByStudentId(Long studentId) {
        try {
            Student student = studentRepository.findById(studentId).orElse(null);
            if (student == null) throw new ApplicationException("Tài khoản không tồn tại");
            List<Enrollment> enrollments = enrollmentRepository.findByStudent_UserId(studentId);
            List<EnrollmentResponse> enrollmentResponses = enrollments.stream().map(enrollment -> new EnrollmentResponse(
                enrollment.getEnrollmentId(),
                enrollment.getStudent().getUserId(),
                enrollment.getCourse().getCourseId(),
                enrollment.getEnrollmentDate(),
                enrollment.getIsComplete()
                )
            ).collect(Collectors.toList());
            return enrollmentResponses;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<CourseResponse> getListCourseFromCart(Long studentId) {
        try {
            Cart cart = cartRepository.findByStudent_UserId(studentId);
            if (cart == null) throw new ApplicationException("Giỏ hàng không tồn tại");
            List<CartItems> cartItems = cartItemsRepository.findAllByCart_CartId(cart.getCartId());
            List<CourseResponse> courseResponses = cartItems.stream().map(cartItem -> courseMapper.toResponse(cartItem.getCourse())).collect(Collectors.toList());
            return courseResponses;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Transactional
    public EnrollmentResponse addStudentToCourse(Long studentId, Long courseId) {
        try {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ApplicationException("Student not found"));
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ApplicationException("Course not found"));

            Enrollment enrollment = Enrollment.builder()
                    .student(student)
                    .course(course)
                    .enrollmentDate(new Date())
                    .isComplete(false)
                    .build();

            Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

            return new EnrollmentResponse(
                    savedEnrollment.getEnrollmentId(),
                    savedEnrollment.getStudent().getUserId(),
                    savedEnrollment.getCourse().getCourseId(),
                    savedEnrollment.getEnrollmentDate(),
                    savedEnrollment.getIsComplete()
            );

        } catch (ApplicationException ex) {
            throw ex;
        }
    }

}
