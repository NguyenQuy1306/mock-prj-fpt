package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.Enrollment;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent_UserId(Long userId);

    @Query("select e from Enrollment e join e.student s join e.course c where s.userId = :userId and c.courseId = :courseId")
    Enrollment findByStudentIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);

    Enrollment findByStudent_UserIdAndCourse_CourseId(Long userId, Long courseId);

    boolean existsByStudent_UserIdAndCourse_CourseId(Long userId, Long courseId);
}
