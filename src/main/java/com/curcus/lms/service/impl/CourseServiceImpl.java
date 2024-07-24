package com.curcus.lms.service.impl;

import com.curcus.lms.model.response.*;
import com.curcus.lms.service.CategorySevice;

import java.util.stream.Collectors;

import java.util.List;
// import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.InvalidFileTypeException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Content;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.entity.Student;
import com.curcus.lms.model.mapper.ContentMapper;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.mapper.SectionMapper;
import com.curcus.lms.model.request.ContentCreateRequest;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.request.CourseRequest;
import com.curcus.lms.model.request.SectionRequest;

import com.curcus.lms.model.response.ContentCreateResponse;
import com.curcus.lms.model.response.CourseDetailResponse2;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.SectionCreateResponse;
import com.curcus.lms.model.response.StudentResponse;
import com.curcus.lms.repository.CategoryRepository;
import com.curcus.lms.repository.ContentRepository;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.InstructorRepository;
import com.curcus.lms.repository.SectionRepository;

import com.curcus.lms.service.CourseService;
import com.curcus.lms.specification.CourseSpecifications;
import com.curcus.lms.service.InstructorService;
import com.curcus.lms.util.FileAsyncUtil;
import com.curcus.lms.util.ValidatorUtil;
import com.curcus.lms.validation.CourseValidator;
import com.curcus.lms.validation.InstructorValidator;

import org.springframework.transaction.annotation.Transactional;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseValidator courseValidator;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private ValidatorUtil validatorUtil;
    @Autowired
    private CategorySevice categoryService;
    @Autowired
    private InstructorValidator instructorValidator;
    @Autowired
    private InstructorService instructorService;

    @Autowired
    private FileAsyncUtil fileAsyncUtil;


    @Override
    public Page<CourseSearchResponse> findAll(Pageable pageable) {
        try {
            Page<Course> coursesPage = courseRepository.findAll(pageable);
            return coursesPage.map(courseMapper::toCourseSearchResponse);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @Override
    public Course findById(Long id) {
        try {
            return courseRepository.findById(id).orElse(null);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @Override
    public Instructor findByIdInstructor(Long id) {
        try {
            return instructorRepository.findById(id).orElse(null);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @Override
    public Page<CourseSearchResponse> findByCategory(Long categoryId, Pageable pageable) {
        try {
            Category category = new Category();
            category.setCategoryId(categoryId);
            Page<Course> coursesPage = courseRepository.findByCategory(category, pageable);
            return coursesPage.map(courseMapper::toCourseSearchResponse);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    // @Override
    // public CourseResponse saveCourse(CourseCreateRequest courseCreateRequest) {
    // // TODO Auto-generated method stub
    // Instructor instructor =
    // instructorRepository.findById(courseCreateRequest.getInstructorId())
    // .orElseThrow(() -> new NotFoundException(
    // "Instructor has not existed with id" +
    // courseCreateRequest.getInstructorId()));
    // Category category =
    // categoryRepository.findById(courseCreateRequest.getCategoryId())
    // .orElseThrow(() -> new NotFoundException(
    // "Category has not existed with id " + courseCreateRequest.getCategoryId()));

    // Course course = courseMapper.toEntity(courseCreateRequest);
    // System.out.println(course.toString());
    // Course savedCourse = courseRepository.save(course);
    // return courseMapper.toResponse(savedCourse);
    // }

    @Override
    public CourseResponse deleteCourse(Long id) {
        // TODO Auto-generated method stub
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Course has not existed with id " + id));
        System.err.println("LLL" + course.getEnrollment().isEmpty());
        if (!course.getEnrollment().isEmpty())
            throw new ValidationException("The course cannot be deleted because someone is currently enrolled");
        // courseRepository.deleteById(id);

        return courseMapper.toResponse(course);
    }

    @Override
    @Transactional
    public CourseResponse saveCourse(CourseCreateRequest courseCreateRequest) {
        // TODO Auto-generated method stub
        Course course = courseMapper.toEntity(courseCreateRequest);
        fileAsyncUtil.validImage(courseCreateRequest.getCourseThumbnail());
        Course savedCourse = courseRepository.save(course);
        fileAsyncUtil.uploadImageAsync(course.getCourseId(), courseCreateRequest.getCourseThumbnail());
        return courseMapper.toResponse(savedCourse);
    }

    @Override
    @Transactional
    public ContentCreateResponse saveContent(ContentCreateRequest contentCreateRequest) {
        // TODO Auto-generated method stub
        Content content = contentMapper.toEntity(contentCreateRequest);
        fileAsyncUtil.validContent(contentCreateRequest.getFile());
        content = contentRepository.save(content);

		fileAsyncUtil.uploadFileAsync(content.getId(), contentCreateRequest.getFile());
        return contentMapper.toResponse(content);
    }

    @Override
    public SectionCreateResponse createSection(SectionRequest sectionRequest) {
        Section section = new Section();
        Course course = courseRepository.findById(sectionRequest.getCourseId())
                .orElseThrow(() -> new NotFoundException(
                        "Course has not existed with id " + sectionRequest.getCourseId()));

        section.setCourse(course);
        section.setSectionName(sectionRequest.getSectionName());
        SectionCreateResponse sectionCreateResponse = sectionMapper.toResponse(sectionRepository.save(section));
        return sectionCreateResponse;
    }

    @Override
    public SectionCreateResponse updateSection(Long sectionId, SectionRequest sectionRequest) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new NotFoundException(
                        "Section not found with id " + sectionId));

        section.setSectionName(sectionRequest.getSectionName());
        SectionCreateResponse sectionUpdateResponse = sectionMapper.toResponse(sectionRepository.save(section));
        return sectionUpdateResponse;
    }

    @Override
    public void checkCourseRequest(CourseRequest courseRequest, BindingResult bindingResult) {
        // Get id of course
        if (findById(courseRequest.getCourseId()) == null) {
            throw new NotFoundException("Course has not existed with id " + courseRequest.getCourseId());
        }
        // Validator to check category and instructor of course
        courseValidator.validate(courseRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new NotFoundException(bindingResult.getFieldError().getDefaultMessage());
        }

    }

    @Override
    public CourseResponse update(CourseRequest courseRequest, BindingResult bindingResult) {
        try {
            // call method check course
            checkCourseRequest(courseRequest, bindingResult);
            // set category entity to course
            Course course = courseMapper.toRequest(courseRequest);
            Category category = categoryService.findById(courseRequest.getCategoryId());
            course.setCategory(category);
            // set instructor entity to course
            Instructor instructor = instructorRepository.findById(courseRequest.getInstructorId()).orElse(null);
            if (instructor == null) {
                throw new NotFoundException("instructor not found with id " + courseRequest.getInstructorId());
            }
            course.setInstructor(instructor);
            // Save update course
            courseRepository.save(course);
            // Mapping course to courseResponse
            CourseResponse courseResponse = courseMapper.toResponse(course);
            return courseResponse;
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
    @Override
    public Page<CourseSearchResponse> searchCourses(
            Long instructorId,
            Long categoryId,
            String title,
            Long price,
            Boolean isFree,
            Pageable pageable) {
        // TODO Auto-generated method stub
        Specification<Course> spec = Specification.where(null);
        if (instructorId != null) {
            spec = spec.and(CourseSpecifications.hasInstructorId(instructorId));
        }

        if (categoryId != null) {
            spec = spec.and(CourseSpecifications.hasCategoryId(categoryId));
        }

        if (title != null) {
            spec = spec.and(CourseSpecifications.hasTitleLike(title));
        }

        if (price != null) {
            spec = spec.and(CourseSpecifications.hasPriceGreaterThanOrEqualTo(price));
        }
        if (isFree != null) {
            spec = spec.and(CourseSpecifications.isFree(isFree));
        }
        Page<Course> coursePage=  courseRepository.findAll(spec, pageable);
        return coursePage.map(courseMapper::toCourseSearchResponse);
    }
    
    @Transactional
    @Override
    public List<CourseDetailResponse2> getCoursebyInstructorId(Long id){
         List<Course> courses = courseRepository.findByInstructorUserId(id);
        return courses.stream().map(this::convertToCourseDetailResponse).collect(Collectors.toList());
    }


    @Override
    public CourseDetailResponse getCourseDetails(Long courseId) {
        Course course = courseRepository.findWithSectionsByCourseId(courseId);
        if (course == null) {
            throw new NotFoundException("Course not found with id " + courseId);
        }
        CourseDetailResponse courseDetailResponse = courseMapper.toDetailResponse(course);
        return courseDetailResponse;
    }


    private CourseDetailResponse2 convertToCourseDetailResponse(Course course) {
        return CourseDetailResponse2.builder()
                .courseId(course.getCourseId())
                .courseThumbnail(course.getCourseThumbnail())
                .title(course.getTitle())
                .description(course.getDescription())
                .price(course.getPrice())
                .categoryId(course.getCategory().getCategoryId())
                .studentList(course.getEnrollment().stream()
                        .map(e -> convertToStudentResponse(e.getStudent()))
                        .collect(Collectors.toList()))
                .createDate(course.getCreatedAt().toLocalDate())
                .status("") // Assuming you want an empty string as default
                .build();
    }

    private StudentResponse convertToStudentResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setStudentId(student.getUserId().intValue());
        response.setName(student.getName());
        // response.setEmail(student.getEmail());
        // response.setFirstName(student.getFirstName());
        // response.setLastName(student.getLastName());
        // response.setPhoneNumber(student.getPhoneNumber());
        return response;
    }
}
