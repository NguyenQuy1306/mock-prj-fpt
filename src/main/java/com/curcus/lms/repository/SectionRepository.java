package com.curcus.lms.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Boolean existsByCourse_Instructor_UserIdAndSectionId(Long instructorId, Long sectionId);
    Optional<Section> findByCourse_CourseIdAndPosition(Long courseId, Long position);
    Optional<Section> findTopByCourse_CourseIdOrderByPositionDesc(Long courseId);
}