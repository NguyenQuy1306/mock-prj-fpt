package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.Enrollment;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    //find All Enrollment that have studentId
    List<Enrollment> findByStudent_UserId(Long studentId);
    //check if the Enrollment of studentId and courseId is already exists
    boolean existsByStudent_UserIdAndCourse_CourseId(Long studentId, Long courseId);
}
