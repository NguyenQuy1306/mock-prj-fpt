package com.curcus.lms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.Cart;
import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.service.CartService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping(value = "/createCart")
    public ResponseEntity<ApiResponse<Cart>> createCart(@RequestParam Long studentId) {
        try {
            Cart cart = cartService.createCart(studentId);
            ApiResponse<Cart> apiResponse = new ApiResponse<>();
            apiResponse.ok(cart);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @PostMapping(value = "/addCourse")
    public ResponseEntity<ApiResponse<CartItems>> addCourseToCart(@RequestParam Long studentId,
            @RequestParam Long courseId) {
        try {
            CartItems cartItem = cartService.addCourseToCart(studentId, courseId);
            ApiResponse<CartItems> apiResponse = new ApiResponse<>();
            apiResponse.ok(cartItem);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @PostMapping(value = "/{studentId}/listCourse")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getListCourseFromCart(@PathVariable Long studentId) {
        try {
            List<CourseResponse> courseResponses = cartService.getListCourseFromCart(studentId);
            ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(courseResponses);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (ValidationException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{studentId}/checkout")
    public ResponseEntity<ApiResponse<Void>> copyCartToOrder(@PathVariable Long studentId) {
        try {
            cartService.copyCartToOrder(studentId);
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.ok();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteCourseFromCart")
    public ResponseEntity<ApiResponse<Void>> deleteCourseFromCart(@RequestParam Long studentId,
            @RequestParam Long cartId,
            @RequestParam Long courseId) {
        try {
            cartService.deleteCourseFromCart(studentId, cartId, courseId);
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.ok();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (ValidationException ex) {
            throw ex;
        }
    }

    @DeleteMapping(value = "/deleteAllCourseFromCart")
    public ResponseEntity<ApiResponse<Void>> deleteAllCourseFromCart(@RequestParam Long studentId,
            @RequestParam Long cartId) {
        try {
            cartService.deleteAllCourseFromCart(studentId, cartId);
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.ok();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (ValidationException ex) {
            throw ex;
        }
    }

    @DeleteMapping(value = "/deleteCart")
    public ResponseEntity<ApiResponse<Void>> deleteCart(@RequestParam Long studentId) {
        try {
            cartService.deleteCart(studentId);
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.ok();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (ValidationException ex) {
            throw ex;
        }
    }
}
