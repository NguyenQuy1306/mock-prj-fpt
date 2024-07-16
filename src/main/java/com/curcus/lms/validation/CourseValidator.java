package com.curcus.lms.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.service.CategoryService;

@Component
public class CourseValidator implements Validator {
    @Autowired
    private CategoryService categoryService;

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

    }

}
