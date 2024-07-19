package com.curcus.lms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Course;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByCategory(Category category, Pageable pageable);

    List<Course> findByCategory(Category category);

    Page<Course> findAll(Pageable pageable);
    Boolean existsByInstructor_UserIdAndCourseId(Long userId, Long courseId);
}
