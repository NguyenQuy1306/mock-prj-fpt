package com.curcus.lms.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.service.CategoryService;
import com.curcus.lms.service.InstructorService;

@Component
public class CourseValidator implements Validator {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private InstructorService instructorService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CourseRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseRequest product = (CourseRequest) target;

        Category category = categoryService.findById(product.getCategoryId());
        if (category == null) {
            errors.rejectValue("categoryId", "error.categoryId", "Category does not exist!");
        }
        Instructor instructor = instructorService.findById(product.getInstructorId());
        if (instructor == null) {
            errors.rejectValue("instructorId", "error.instructorId", "Instructor does not exist!");
        }

    }

}
