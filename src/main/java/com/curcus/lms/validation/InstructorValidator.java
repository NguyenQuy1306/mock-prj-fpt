package com.curcus.lms.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.service.InstructorService;

@Component
public class InstructorValidator implements Validator {

    @Autowired
    private InstructorService instructorService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CourseRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseRequest courseRequest = (CourseRequest) target;
        Instructor instructor = instructorService.findById(courseRequest.getInstructorId());
        if (instructor == null) {
            errors.rejectValue("instructorId", "error.instructorId", "Instructor does not exist!");
        }
    }
}
