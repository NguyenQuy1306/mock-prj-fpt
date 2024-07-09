package com.curcus.lms.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.service.CartService;
import com.curcus.lms.service.CourseService;
import com.curcus.lms.service.StudentService;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CourseService courseService;
    @Autowired
    StudentService studentService;

    @Override
    public CartItems addCourseToCart(CourseRequest courseRequest, BindingResult bindingResult) {
        try {
            // Check valid course
            courseService.checkCourseRequest(courseRequest, bindingResult);
            // check enrolled course
            return new CartItems();
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
}
