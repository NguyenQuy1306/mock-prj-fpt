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
import com.curcus.lms.repository.CartItemsRepository;
import com.curcus.lms.repository.CartRepository;
import com.curcus.lms.repository.EnrollmentRepository;
import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.service.CartService;
import com.curcus.lms.service.CourseService;
import com.curcus.lms.service.EnrollmentService;
import com.curcus.lms.service.StudentService;
import java.util.Optional;
import java.util.List;

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
    private CartItemsRepository cartItemRepository;

    @Override
    public Cart getCartById(Long studentId) {
        return cartRepository.getCartNotPaidByStudentId(studentId);
    }

    @Override
    public CartItems getById(Long cartId, Long courseId) {
        CartItemsId cartItemsId = new CartItemsId(cartId, courseId);
        return cartItemRepository.findById(cartItemsId).orElse(null);
    }

    @Override
    public Cart createCart(Long studentId) {
        // check valid studentId
        try {
            if (studentService.findById(studentId) == null) {
                throw new NotFoundException("Student has not existed with id " + studentId);
            }
            System.out.println("studentIdstudentId" + studentId);
            Cart cart = getCartById(studentId);
            if (cart == null) {
                cart = new Cart();
                Student student = studentRepository.findById(studentId).orElse(null);
                cart.setStudent(student);
                cartRepository.save(cart);
            } else {
                throw new ValidationException("Cart has already created for studentId " + studentId);
            }
            return cart;
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @Override
    public CartItems addCourseToCart(Long studentId, Long courseId) {
        try {
            // check valid courseId
            Course course = courseService.findById(courseId);
            if (course == null) {
                throw new NotFoundException("Course has not existed with id " + courseId);
            }
            // check valid studentId
            if (studentService.findById(studentId) == null) {
                throw new NotFoundException("Student has not existed with id " + studentId);
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
                Student student = studentRepository.findById(studentId).orElse(null);
                cart.setStudent(student);
                cartRepository.save(cart);
            }
            // check course alreay in cart
            if (getById(cart.getCartId(), courseId) != null) {
                throw new ValidationException("CourseId " + courseId + " already in cart");
            }

            // add course
            // create cartItems and save
            CartItemsId cartItemsId = new CartItemsId(cart.getCartId(), courseId);
            CartItems cartItems = new CartItems();
            cartItems.setId(cartItemsId);
            cartItems.setCart(cart);
            cartItems.setCourse(course);
            cartItemRepository.save(cartItems);
            // Update totalPriceOfCart
            cartRepository.save(cart);
            return cartItems;
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @Override
    public void deleteCourseFromCart(Long studentId, Long cartId, Long courseId) {
        try {
            // check valid courseId
            Course course = courseService.findById(courseId);
            if (course == null) {
                throw new NotFoundException("Course has not existed with id " + courseId);
            }
            // check valid studentId
            if (studentService.findById(studentId) == null) {
                throw new NotFoundException("Student has not existed with id " + studentId);
            }
            // Check cart is created or not
            if (cartRepository.getCartByCartIdAndStudentId(studentId, cartId) == null) {
                throw new ValidationException("Cart doesn't exist for studentId: " + studentId);
            }
            // Check if course is in cart
            if (getById(cartId, courseId) == null) {
                throw new ValidationException("CourseId " + courseId + " not found in cart");
            }
            // delete cart
            CartItemsId cartItemsId = new CartItemsId(cartId, courseId);
            cartItemRepository.deleteById(cartItemsId);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @Override
    public void deleteAllCourseFromCart(Long studentId, Long cartId) {
        try {
            // check valid courseId
            // check valid studentId
            if (studentService.findById(studentId) == null) {
                throw new NotFoundException("Student has not existed with id " + studentId);
            }
            // Check cart is created or not
            if (cartRepository.getCartByCartIdAndStudentId(studentId, cartId) == null) {
                throw new ValidationException("Cart doesn't exist for studentId " + studentId);
            }
            List<Cart> carts = cartItemRepository.findAllCourseWithCartId(cartId);
            if (carts.isEmpty()) {
                throw new NotFoundException("No course exists in cartId " + cartId);
            }
            // delete allCourseInCart
            cartItemRepository.deleteCartItemsById(cartId);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @Override
    public void deleteCart(Long studentId) {
        try {
            // check valid courseId
            // check valid studentId
            if (studentService.findById(studentId) == null) {
                throw new NotFoundException("Student has not existed with id " + studentId);
            }

            Cart cart = cartRepository.getCartByStudentId(studentId);
            if (cart == null) {
                throw new NotFoundException("Cart doesn't exist for studentId " + studentId);
            }
            do {
                Long cartId = cart.getCartId();
                cartItemRepository.deleteCartItemsById(cartId);
                cartRepository.deleteById(cartId);
                cart = cartRepository.getCartByStudentId(studentId);
            } while (cart != null);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
}
