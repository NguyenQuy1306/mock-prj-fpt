package com.curcus.lms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.entity.Cart;
import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.response.CartResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.repository.CartItemsRepository;
import com.curcus.lms.repository.CartRepository;
import com.curcus.lms.repository.EnrollmentRepository;
import com.curcus.lms.repository.StudentRepository;
import com.curcus.lms.service.CartService;
import com.curcus.lms.service.CourseService;
import com.curcus.lms.service.EnrollmentService;
import com.curcus.lms.service.StudentService;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;


    @Override
    public CartResponse getById(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        CartResponse cartResponse = new CartResponse(cart.getCartId(), cart.getStudent());

        return cartResponse;
    }

    @Override
    public CartResponse getCartByStudentId(Long studentId){
        try {
            Cart cart = cartRepository.findCartByStudent_StudentId(studentId);
            if(cart == null) {
                throw new NotFoundException("Cart not found");
            }
            CartResponse cartResponse = new CartResponse(cart.getCartId(), cart.getStudent());
            return cartResponse;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<CourseResponse> getListCourseFromCart(Long studentId){
        try{
            Cart cart = cartRepository.findCartByStudent_StudentId(studentId);
            if(cart == null) {
                throw new NotFoundException("Cart not found");
            }
            List<CartItems> cartItems = cartItemsRepository.findAllByCart_CartId(cart.getCartId());
            List<CourseResponse> courseResponses = courseMapper.toResponseList(cartItems.stream().map(CartItems::getCourse).collect(Collectors.toList()));
            return courseResponses;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
