package com.curcus.lms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.response.CourseResponse;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>,JpaSpecificationExecutor<Course> {
    Page<Course> findByCategory(Category category, Pageable pageable);

    List<Course> findByCategory(Category category);

    Page<Course> findAll(Pageable pageable);
    Boolean existsByInstructor_UserIdAndCourseId(Long userId, Long courseId);

    // @Query(value = "SELECT * FROM courses c WHERE c.instructor_id = :instructorId", nativeQuery = true)
    List<Course> findByInstructorUserId(@Param("instructorId") Long instructorId);
}
