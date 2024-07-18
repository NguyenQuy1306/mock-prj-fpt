package com.curcus.lms.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.curcus.lms.constants.ContentType;
import com.curcus.lms.exception.InvalidFileTypeException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.repository.CategoryRepository;
import com.curcus.lms.repository.InstructorRepository;
import com.curcus.lms.repository.UserRepository;
import com.curcus.lms.service.CloudinaryService;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CourseMapper {
	@Autowired
    protected CloudinaryService cloudinaryService;

    @Autowired
    protected InstructorRepository instructorRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Mapping(source = "course.instructor.userId", target = "instructorId")
    @Mapping(source = "course.category.categoryId", target = "categoryId")

    public abstract CourseResponse toResponse(Course course);

    public abstract List<CourseResponse> toResponseList(List<Course> courses);

    // @Mapping(target = "instructor", expression =
    // "java(findUserById(courseCreateRequest.getInstructorId()))")
    // @Mapping(target = "category", expression =
    // "java(findCategoryById(courseCreateRequest.getCategoryId()))")
    // public abstract Course toEntity(CourseCreateRequest courseCreateRequest);

    // protected User findUserById(Long id) {
    // return userRepository.findById(id).orElse(null);
    @Mapping(target = "instructor", expression = "java(findInstructorById(courseCreateRequest.getInstructorId()))")
    @Mapping(target = "category", expression = "java(findCategoryById(courseCreateRequest.getCategoryId()))")
    @Mapping(target = "courseThumbnail", expression = "java(uploadAndGetUrl(courseCreateRequest.getCourseThumbnail()))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    public abstract Course toEntity(CourseCreateRequest courseCreateRequest);

    protected Instructor findInstructorById(Long id) {
        return instructorRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Instructor has not existed with id " + id));
    }

    protected Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category has not existed with id " + id));
    }
    protected String uploadAndGetUrl(MultipartFile file) {
        ContentType contentType = getContentType(file);
        try {
            switch (contentType) {
                case IMAGE:
                    return cloudinaryService.uploadImage(file);
                default:
                    throw new InvalidFileTypeException("Unsupported file type");
            }
        } catch (IOException | InvalidFileTypeException e) {
            throw new InvalidFileTypeException("Unsupported file type");
        }
    }
    protected ContentType getContentType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null) {
            if (contentType.startsWith("video")) {
                return ContentType.VIDEO;
            } else if (contentType.startsWith("image")) {
                return ContentType.IMAGE;
            } else {
                return ContentType.DOCUMENT;
            }
        } else {
            return ContentType.UNKNOWN;
        }
    }
}
