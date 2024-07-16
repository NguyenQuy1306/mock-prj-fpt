package com.curcus.lms.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.curcus.lms.model.entity.*;
import com.curcus.lms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.response.CartResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.service.CartService;
import com.curcus.lms.service.CourseService;
import com.curcus.lms.service.EnrollmentService;
import com.curcus.lms.service.StudentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private StudentService StudentService;


    @Override
    public CartResponse getById(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        CartResponse cartResponse = new CartResponse(cart.getCartId(), cart.getStudent());

        return cartResponse;
    }

    @Override
    public CartResponse getCartByStudentId(Long studentId){
        try {
            Cart cart = cartRepository.findCartByStudent_UserId(studentId);
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
            Cart cart = cartRepository.findCartByStudent_UserId(studentId);
            if(cart == null) {
                throw new NotFoundException("Cart not found");
            }
            List<CartItems> cartItems = cartItemsRepository.findAllByCart_CartId(cart.getCartId());
            if(cartItems == null){
                return List.of();
            }
            List<CourseResponse> courseResponses = courseMapper.toResponseList(cartItems.stream().map(CartItems::getCourse).collect(Collectors.toList()));
            return courseResponses;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Transactional
    public void copyCartToOrder(Long studentId) {
        try {
            Cart cart = cartRepository.findCartByStudent_UserId(studentId);
            if (cart == null) {
                throw new NotFoundException("Cart not found");
            }
            List<CartItems> cartItems = cartItemsRepository.findAllByCart_CartId(cart.getCartId());

            if (cartItems.isEmpty()) {
                throw new NotFoundException("No items in the cart");
            }

            Long totalPrice = cartItems.stream()
                    .map(cartItem -> cartItem.getCourse().getPrice())
                    .reduce(0L, Long::sum);

            Order order = Order.builder()
                    .student(cart.getStudent())
                    .paymentDate(new Date())
                    .totalPrice(totalPrice)
                    .build();

            Order savedOrder = orderRepository.save(order);

            List<OrderItems> orderItemsList = new ArrayList<>();
            for (CartItems cartItem : cartItems) {
                OrderItems orderItem = OrderItems.builder()
                        .id(new OrderItemsId(savedOrder.getOrderId(), cartItem.getCourse().getCourseId()))
                        .order(savedOrder)
                        .course(cartItem.getCourse())
                        .price(cartItem.getCourse().getPrice())
                        .build();
                StudentService.addStudentToCourse(studentId, cartItem.getCourse().getCourseId());
                orderItemsList.add(orderItem);
            }

            orderItemsRepository.saveAll(orderItemsList);
            //StudentService.addStudentToCoursesFromCart(studentId);
            //add delete cart method

        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while copying the cart to the order", ex);
        }
    }
}
