package com.curcus.lms.service.impl;

import java.io.Console;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Content;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Section;

import com.curcus.lms.model.mapper.ContentMapper;
import com.curcus.lms.model.mapper.CourseMapper;
import com.curcus.lms.model.mapper.SectionMapper;
import com.curcus.lms.model.request.ContentCreateRequest;
import com.curcus.lms.model.request.ContentUpdatePositionRequest;
import com.curcus.lms.model.request.ContentUpdateRequest;
import com.curcus.lms.model.request.CourseCreateRequest;
import com.curcus.lms.model.request.SectionRequest;

import com.curcus.lms.model.response.ContentCreateResponse;
import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.model.response.SectionCreateResponse;
import com.curcus.lms.repository.CategoryRepository;
import com.curcus.lms.repository.ContentRepository;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.InstructorRepository;
import com.curcus.lms.repository.SectionRepository;

import com.curcus.lms.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
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

    @Override
    public List<CourseResponse> findAll() {
        return courseMapper.toResponseList(courseRepository.findAll());
    }

    @Override
    public List<CourseResponse> findByCategory(int categoryId) {
        try {
            Category category = new Category();
            category.setCategoryId(categoryId);
            return courseMapper.toResponseList(courseRepository.findByCategory(category));
        } catch (ApplicationException ex) {
            throw ex;
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
        if (!course.getEnrollment().isEmpty())
            throw new ValidationException("The course cannot be deleted because someone is currently enrolled");
        courseRepository.deleteById(id);

        return courseMapper.toResponse(course);
    }

    @Override
    public CourseResponse saveCourse(CourseCreateRequest courseCreateRequest) {
        // TODO Auto-generated method stub
        Course course = courseMapper.toEntity(courseCreateRequest);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponse(savedCourse);
    }

    @Override
    public ContentCreateResponse saveContent(ContentCreateRequest contentCreateRequest) {
        // TODO Auto-generated method stub
        Content content = contentMapper.toEntity(contentCreateRequest);
        content = contentRepository.save(content);
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
        SectionCreateResponse sectionCreateResponse=sectionMapper.toResponse(sectionRepository.save(section));
        return sectionCreateResponse;
    }

    // @Override
    // public ContentCreateResponse updateContent(Long id, ContentUpdateRequest contentUpdateRequest) {
    //     Content content = contentRepository.findById(contentUpdateRequest.getId())
    //                 .orElseThrow(() -> new ApplicationException("Content not found"));
    //     content = contentMapper.toEntity(contentUpdateRequest);
    //     content = contentRepository.save(content);
    //     return contentMapper.toResponse(content);
    // }

    @Override
    public List<ContentCreateResponse> updateContentPositions(Long id, List<ContentUpdatePositionRequest> positionUpdates){
        try{
            Section section = sectionRepository.findById(id)
            .orElseThrow(() -> new ApplicationException("Section not found with id: " + id));
            
            List<Content> updatedContents = new ArrayList<>();
            for (ContentUpdatePositionRequest update : positionUpdates) {
                Content content = contentRepository.findById(update.getContentId())
                    .orElseThrow(() -> new ApplicationException("Content not found"));
        
                content.setPosition(update.getNewPosition());
                updatedContents.add(content);
                contentRepository.save(content);
            }
            updatedContents.sort(Comparator.comparingLong(Content::getPosition));
            boolean needsAdjustment = false;
            for (int i = 0; i < updatedContents.size()-1; i++) {
                if (updatedContents.get(i).getPosition()==updatedContents.get(i+1).getPosition()) {
                    throw new ApplicationException("Position is invalid");
                }
            }
            for (int i = 0; i < updatedContents.size(); i++) {
                if (updatedContents.get(i).getPosition() != i + 1) {
                    needsAdjustment = true;
                    break;
                }
            }

            if (needsAdjustment) {
                for (int i = 0; i < updatedContents.size(); i++) {
                    Content content = updatedContents.get(i);
                    content.setPosition((long) (i + 1));
                    contentRepository.save(content);
                }
            }

            List<ContentCreateResponse> responseList = new ArrayList<>();
            for (Content content : updatedContents) {
                ContentCreateResponse response = contentMapper.toResponse(content);
                responseList.add(response);
            }

            return responseList;
            // return updatedContents.stream()
            //                         .map(contentMapper::toResponse)
            //                         .collect(Collectors.toList());
        }catch(ApplicationException ex){
            throw ex;
        }
    }
}
