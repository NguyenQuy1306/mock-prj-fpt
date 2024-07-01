package com.curcus.lms.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.curcus.lms.model.dto.InstructorDTO;
import com.curcus.lms.model.entity.Instructor;

@Mapper
public interface InstructorMapper {

    @Mapping(source = "userId", target = "userId")
    InstructorDTO toInstructorDTO(Instructor instructor);

    List<InstructorDTO> toInstructorDTOs(List<Instructor> instructors);
}