package com.curcus.lms.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.Cart;
import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.entity.CartItemsId;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Enrollment;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.repository.CartItemRepository;
import com.curcus.lms.repository.CartRepository;
import com.curcus.lms.repository.EnrollmentRepository;
import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.service.CartService;
import com.curcus.lms.service.CourseService;
import com.curcus.lms.service.EnrollmentService;
import com.curcus.lms.service.StudentService;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart getCartById(Long studentId) {
        boolean isPaid = false;
        return cartRepository.getCartNotPaidByStudentId(studentId, isPaid);
    }

    @Override
    public CartItems getById(Long cartId, Long courseId) {
        CartItemsId cartItemsId = new CartItemsId(cartId, courseId);
        return cartItemRepository.findById(cartItemsId).orElse(null);
    }

    @Override
    public CartItems addCourseToCart(Long studentId, Long courseId) {
        try {
            // check valid courseId
            Course course = courseService.findById(courseId);
            if (course == null) {
                throw new NotFoundException("course not found");
            }
            // check valid studentId
            if (studentService.findById(studentId) == null) {
                throw new NotFoundException("student not found");
            }
            // check enrolled course
            Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);

            if (enrollment != null) {
                throw new ValidationException("Course already enrolled");
            }
            // Check cart is created or not
            Cart cart = getCartById(studentId);
            if (cart == null) {
                cart = new Cart();
                Student student = studentRepository.findById(studentId).get();
                cart.setStudent(student);
                cart.setIsPaid(false);
                cartRepository.save(cart);
            }
            // check course alreay in cart
            if (getById(cart.getCartId(), courseId) != null) {
                throw new ValidationException("Course already in cart");
            }

            // add course
            // create cartItems and save
            CartItemsId cartItemsId = new CartItemsId(cart.getCartId(), courseId);
            CartItems cartItems = new CartItems();
            cartItems.setId(cartItemsId);
            cartItems.setCart(cart);
            cartItems.setCourse(course);
            cartItemRepository.save(cartItems);
            return cartItems;
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
}
