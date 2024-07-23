package com.curcus.lms.model.mapper;

import com.curcus.lms.model.response.ContentDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.curcus.lms.constants.ContentType;
import com.curcus.lms.exception.InvalidFileTypeException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.model.entity.Content;
import com.curcus.lms.model.entity.Section;
import com.curcus.lms.model.request.ContentCreateRequest;
import com.curcus.lms.model.response.ContentCreateResponse;
import com.curcus.lms.repository.ContentRepository;
import com.curcus.lms.repository.CourseRepository;
import com.curcus.lms.repository.SectionRepository;
import com.curcus.lms.service.CloudinaryService;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class ContentMapper {

    
    @Autowired
    protected SectionRepository sectionRepository;
    @Autowired
    protected CourseRepository courseRepository;
    @Autowired
    protected ContentRepository contentRepository;

    @Mapping(source = "type", target = "type")
    @Mapping(source = "url", target = "url")
    @Mapping(source = "position", target = "position")
    public abstract ContentDetailResponse toDetailResponse(Content content);

    @Mapping(expression = "java(content.getSection().getSectionId())", target = "sectionId")
    public abstract ContentCreateResponse toResponse(Content content);

    @Mapping(target = "section", expression = "java(findSectionById(contentCreateRequest.getSectionId()))")
//    @Mapping(target = "url", expression = "java(uploadAndGetUrl(contentCreateRequest.getFile()))")
    @Mapping(target = "url", expression = "java(null)")
    @Mapping(target = "type", expression = "java(getContentType(contentCreateRequest.getFile()))")
    @Mapping(target = "position", source = "contentCreateRequest.sectionId", qualifiedByName = "getLastPosition")
    public abstract Content toEntity(ContentCreateRequest contentCreateRequest);


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

    protected Section findSectionById(Long id) {
        return sectionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Section has not existed with id " + id));
    }

    @Named("getLastPosition")
    public Long getLastPosition(Long sectionId) {
        Section section = findSectionById(sectionId);
        return section.getContents().stream()
                .map(Content::getPosition)
                .max(Long::compare)
                .orElse(0L) + 1;
    }
}
