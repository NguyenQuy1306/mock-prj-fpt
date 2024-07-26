package com.curcus.lms.service.impl;

import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.*;
import com.curcus.lms.model.request.UserAddressRequest;
import com.curcus.lms.model.response.UserAddressResponse;
import com.curcus.lms.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Service;

import com.curcus.lms.repository.CartItemsRepository;
import com.curcus.lms.repository.CartRepository;
import com.curcus.lms.repository.EnrollmentRepository;
import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.service.StudentService;

import jakarta.validation.ConstraintViolationException;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.entity.Cart;
import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.entity.Enrollment;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.exception.DuplicatePhoneNumberException;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.mapper.UserMapper;
import com.curcus.lms.model.request.StudentRequest;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.EnrollmentResponse;
import com.curcus.lms.model.response.StudentStatisticResponse;
import com.curcus.lms.model.response.StudentResponse;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.*;

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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

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
            if (studentRepository.existsByEmail(studentRequest.getEmail())) throw new ApplicationException("This email address already exists");
            if (studentRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) throw new ApplicationException("PhoneNumber already exists");
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
            if (!studentRepository.existsById(studentId)) throw new ApplicationException("Account does not exist");
            Student newStudent = studentRepository.findById(studentId).orElse(null);
            newStudent.setName(studentRequest.getName());
            newStudent.setFirstName(studentRequest.getFirstName());
            newStudent.setLastName(studentRequest.getLastName());

            if(!newStudent.getPhoneNumber().equals(studentRequest.getPhoneNumber())) {
                if (studentRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) throw new ApplicationException("PhoneNumber already exists");
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
            if (!studentRepository.existsById(studentId)) throw new ApplicationException("Account does not exist");
            Student newStudent = studentRepository.findById(studentId).orElse(null);

            if(studentRequest.getPassword() == "") throw new ApplicationException("Please enter a password");
            if(passwordEncoder.matches(studentRequest.getPassword(), newStudent.getPassword())) throw new ApplicationException("New password cannot be the same as the old password");
            newStudent.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
            return userMapper.toResponse(studentRepository.save(newStudent));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public void deleteStudent(Long studentId) {
        try {
            if (!studentRepository.existsById(studentId)) throw new ApplicationException("Accout does not exist");
            studentRepository.deleteById(studentId);
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public List<EnrollmentResponse> getCoursesByStudentId(Long studentId) {
        try {
            Student student = studentRepository.findById(studentId).orElse(null);
            if (student == null) throw new ApplicationException("Account does not exist");
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
            Cart cart = cartRepository.findCartByStudent_UserId(studentId);
            if (cart == null) throw new ApplicationException("Cart not found");
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

    @Transactional
    public List<EnrollmentResponse> addStudentToCoursesFromCart(Long studentId) {
        List<CourseResponse> courseResponses = getListCourseFromCart(studentId);
        List<Long> courseIds = courseResponses.stream()
                .map(CourseResponse::getCourseId)
                .collect(Collectors.toList());

        List<EnrollmentResponse> enrollmentResponses = new ArrayList<>();

        for (Long courseId : courseIds) {
            try {
                EnrollmentResponse enrollmentResponse = addStudentToCourse(studentId, courseId);
                enrollmentResponses.add(enrollmentResponse);
            } catch (ApplicationException ex) {
                throw ex;
            }
        }

        return enrollmentResponses;
    }

    @Override
    public  HashMap<String, Integer> getCoursesPurchasedLastFiveYears(Long studentId){

        Student student = studentRepository.findById(studentId).orElse(null);

        if (student==null) throw new NotFoundException("Student doesn't exist");

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

        HashMap<String, Integer> courseCount = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            int year = LocalDate.now().getYear() - i;
            LocalDate startOfYear = LocalDate.of(year, 1, 1);
            LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
            int count = 0;
            for (int j = 0; j < enrollments.size(); j++) {
                Enrollment enrollment = enrollments.get(j);
                LocalDate enrollmentDate = enrollment.getEnrollmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if ((enrollmentDate.isAfter(startOfYear) || enrollmentDate.isEqual(startOfYear)) &&
                    (enrollmentDate.isBefore(endOfYear) || enrollmentDate.isEqual(endOfYear))) {
                    count++;
                }
            }
            String temp=String.valueOf(year);
            courseCount.put(temp, count);
        }

        return courseCount;
    }

    @Override
    public  Integer getTotalPurchaseCourse(Long studentId){

        Student student = studentRepository.findById(studentId).orElse(null);

        if (student==null) throw new NotFoundException("Student doesn't exist");

        return enrollmentRepository.totalPurchaseCourse(studentId);
    }

    @Override
    public Integer totalFinishCourse(Long studentId){
        try {
            List<Enrollment> enrollments = enrollmentRepository.findByStudent_UserId(studentId);
            int totalFinishCourse = (int) enrollments.stream()
                   .filter(Enrollment::getIsComplete)
                   .count();
            return totalFinishCourse;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public HashMap<String, Integer> finishCourseFiveYears(Long studentId){
        try {
            List<Enrollment> enrollments = enrollmentRepository.findByStudent_UserId(studentId);
            Year currentYear = Year.now();
            HashMap<String, Integer> finishCourseFiveYears = new HashMap<String, Integer>();
            Stream.iterate(currentYear, year -> year.minusYears(1)).limit(5).forEach(year -> {
                String yearString = year.toString();
                Integer count = (int) enrollments.stream()
                       .filter(enrollment -> enrollment.getEnrollmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                               .getYear() == year.getValue())
                       .filter(Enrollment::getIsComplete)
                       .count();
                finishCourseFiveYears.put(yearString, count);
            });
            return finishCourseFiveYears;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public StudentStatisticResponse studentStatistic(Long studentId)
    {
        StudentStatisticResponse statisticResponse=new StudentStatisticResponse(getTotalPurchaseCourse(studentId),
                                                                  totalFinishCourse(studentId),
                                                                  getCoursesPurchasedLastFiveYears(studentId),
                                                                  finishCourseFiveYears(studentId));
        return statisticResponse;
    }

    public UserAddressResponse updateStudentAddress(Long userId, UserAddressRequest addressRequest) {
        try {
            Student user = studentRepository.findById(userId)
                    .orElseThrow(() -> new ApplicationException("Student not found with id: " + userId));

            Optional.ofNullable(addressRequest.getFirstName()).ifPresent(user::setFirstName);
            Optional.ofNullable(addressRequest.getLastName()).ifPresent(user::setLastName);
            Optional.ofNullable(addressRequest.getPhoneNumber()).ifPresent(user::setPhoneNumber);
            Optional.ofNullable(addressRequest.getUserAddress()).ifPresent(user::setUserAddress);
            Optional.ofNullable(addressRequest.getUserCity()).ifPresent(user::setUserCity);
            Optional.ofNullable(addressRequest.getUserCountry()).ifPresent(user::setUserCountry);
            Optional.ofNullable(addressRequest.getUserPostalCode()).ifPresent(user::setUserPostalCode);
            studentRepository.save(user);
            return userMapper.toUserAddressResponse(user);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new DuplicatePhoneNumberException(
                        "Phone number " + addressRequest.getPhoneNumber() + " already exists.");
            }
            throw ex;
        }

        catch (com.curcus.lms.exception.NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        }
    }
}
