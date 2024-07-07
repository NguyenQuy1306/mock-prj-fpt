package com.curcus.lms.model.mapper;

import java.io.IOException;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.curcus.lms.exception.InvalidFileTypeException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.entity.VideoContent;
import com.curcus.lms.model.request.VideoContentCreateRequest;
import com.curcus.lms.model.response.VideoContentCreateResponse;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.SectionRepository;
import com.curcus.lms.service.CloudinaryService;

@Mapper(componentModel = "spring")
public abstract class VideoContentMapper {
    @Autowired
    protected CloudinaryService cloudinaryService;
    @Autowired
    protected SectionRepository sectionRepository;
    @Autowired
    protected CourseRepository courseRepository;

    @Mapping(target = "course", expression = "java(findCourseById(videoContentCreateRequest.getCourseId()))")
    @Mapping(target = "section", expression = "java(findSectionById(videoContentCreateRequest.getSectionId()))")
    @Mapping(target = "videoUrl", expression = "java(uploadVideoAndGetUrl(videoContentCreateRequest.getCourseVideo()))")
    @Mapping(target = "videoType", expression = "java(getVideoType(videoContentCreateRequest.getCourseVideo()))")
    public abstract VideoContent toEntity(VideoContentCreateRequest videoContentCreateRequest);

    protected String uploadVideoAndGetUrl(MultipartFile file) {
        try {
            return cloudinaryService.uploadVideo(file);
        } catch (IOException | InvalidFileTypeException e) {
            throw new RuntimeException("Error uploading video", e);
        }
    }

    protected String getVideoType(MultipartFile file) {
        return file.getContentType();
    }

    protected Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Course not existed with id " + id));
    }

    protected Section findSectionById(Long id) {
        return sectionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Section has not existed with id " + id));
    }

    public abstract VideoContentCreateResponse toResponse(VideoContent videoContent);
}
