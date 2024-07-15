package com.curcus.lms.repository;

import com.curcus.lms.model.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByStudent_UserIdAndCourse_CourseId(Long studentId, Long courseId);
}
