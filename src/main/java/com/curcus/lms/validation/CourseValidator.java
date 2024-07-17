package com.curcus.lms.validation;

import com.curcus.lms.service.CategorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.request.CourseRequest;
<<<<<<< HEAD
import com.curcus.lms.service.CategoryService;
import com.curcus.lms.service.InstructorService;
=======
>>>>>>> origin/merge

@Component
public class CourseValidator implements Validator {
    @Autowired
<<<<<<< HEAD
    private CategoryService categoryService;
    @Autowired
    private InstructorService instructorService;
=======
    private CategorySevice categorySevice;
>>>>>>> origin/merge

    @Override
    public boolean supports(Class<?> clazz) {
        return CourseRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseRequest product = (CourseRequest) target;
<<<<<<< HEAD
        Category category = categoryService.findById(product.getCategoryId());
=======

        Category category = categorySevice.findById(product.getCategoryId());
>>>>>>> origin/merge
        if (category == null) {
            errors.rejectValue("categoryId", "error.categoryId",
                    "Category has not existed with id " + product.getCategoryId());
        }
        Instructor instructor = instructorService.findById(product.getInstructorId());
        if (instructor == null) {
            errors.rejectValue("instructorId", "error.instructorId",
                    "Instructor has not existed with id " + product.getInstructorId());
        }

    }

}
