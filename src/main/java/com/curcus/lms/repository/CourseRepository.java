package com.curcus.lms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
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
    Page<Course> findAll(Pageable pageable);
//    Page<Course> findByTitleContainingIgnoreCase(String name,Pageable pageable);

}
