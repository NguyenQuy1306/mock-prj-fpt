package com.curcus.lms.model.mapper;

import com.curcus.lms.model.entity.Content;
import com.curcus.lms.model.response.*;
import com.curcus.lms.repository.*;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.curcus.lms.constants.ContentType;
import com.curcus.lms.exception.InvalidFileTypeException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.service.CloudinaryService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Comparator;

@Mapper(componentModel = "spring")
public abstract class CourseMapper {
    @Autowired
    protected CloudinaryService cloudinaryService;

    @Autowired
    protected InstructorRepository instructorRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected RatingRepository ratingRepository;

    @Autowired
    protected InstructorMapper instructorMapper;

    @Autowired
    protected CourseRepository courseRepository;

    @Mapping(source = "courseThumbnail", target = "courseThumbnail")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "avgRating", target = "avgRating")
    @Mapping(source = "instructor", target = "instructor")
    @Mapping(source = "category", target = "category")
    @Mapping(target = "sections", expression = "java(mapSortedSections(course))")
    public abstract CourseDetailResponse toDetailResponse(Course course);

    protected List<SectionDetailResponse> mapSortedSections(Course course) {
        return course.getSections().stream()
                .sorted(Comparator.comparing(Section::getPosition))
                .map(this::mapSection)
                .collect(Collectors.toList());
    }

    protected abstract SectionDetailResponse mapSection(Section section);


    @Mapping(source = "course.instructor.userId", target = "instructorId")
    @Mapping(source = "course.category.categoryId", target = "categoryId")
    public abstract CourseResponse toResponse(Course course);

    @Mapping(source = "course.instructor.name", target = "instructorName")
    @Mapping(source = "course.category.categoryName", target = "categoryName")
    public abstract CourseResponseForCart toResponseCourseCart(Course course);

    public abstract List<CourseResponse> toResponseList(List<Course> courses);

    // @Mapping(target = "instructor", expression =
    // "java(findUserById(courseCreateRequest.getInstructorId()))")
    // @Mapping(target = "category", expression =
    // "java(findCategoryById(courseCreateRequest.getCategoryId()))")
    // public abstract Course toEntity(CourseCreateRequest courseCreateRequest);
    @Mapping(target = "instructor.userId", source = "instructorId")
    @Mapping(target = "category.categoryId", source = "categoryId")
    public abstract Course toRequest(CourseRequest courseRequest);

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

    // CourseSearchResponse
    @Mapping(target = "totalReviews", source = "totalRating")
    @Mapping(source = "course.category.categoryName", target = "categoryName")
    @Mapping(target = "prePrice", expression = "java(getPrePriceByCourseId(course.getCourseId()))")
    @Mapping(target = "aftPrice", expression = "java(getAftPriceByCourseId(course.getCourseId()))")
    public abstract CourseSearchResponse toCourseSearchResponse(Course course);

    public abstract List<CourseSearchResponse> toCourseSearchResponseList(List<Course> courses);

    @Named("getPrePriceByCourseId")
    protected Long getPrePriceByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            return course.getPrice();
        }
        return null;
    }

    @Named("getAftPriceByCourseId")
    protected Long getAftPriceByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            return course.getPrice();
        }
        return null;
    }

}
