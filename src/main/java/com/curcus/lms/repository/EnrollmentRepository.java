package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.Enrollment;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent_UserId(Long studentId);

    @Query("select e from Enrollment e join e.student s join e.course c where s.userId = :studentId and c.courseId = :courseId")
    Enrollment findByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    Enrollment findByStudent_UserIdAndCourse_CourseId(Long studentId, Long courseId);

}
