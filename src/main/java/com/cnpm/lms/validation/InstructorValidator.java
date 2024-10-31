// package com.cnpm.lms.validation;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
// import org.springframework.validation.Errors;
// import org.springframework.validation.Validator;

// import com.cnpm.lms.exception.NotFoundException;
// import com.cnpm.lms.model.entity.Instructor;
// // import com.cnpm.lms.model.request.CourseRequest;
// import com.cnpm.lms.model.response.InstructorResponse;
// import com.cnpm.lms.service.InstructorService;

// import java.util.Optional;

// @Component
// public class InstructorValidator implements Validator {

// @Autowired
// private InstructorService instructorService;

// // @Override
// // public boolean supports(Class<?> clazz) {
// // return CourseRequest.class.equals(clazz);
// // }

// // @Override
// // public void validate(Object target, Errors errors) {
// // CourseRequest courseRequest = (CourseRequest) target;
// // Optional<InstructorResponse> instructor =
// // instructorService.findById(courseRequest.getInstructorId());
// // if (instructor == null) {
// // errors.rejectValue("instructorId", "error.instructorId", "Instructor does
// not
// // exist!");
// // }
// // }
// }
