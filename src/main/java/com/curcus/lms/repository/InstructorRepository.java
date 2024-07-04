package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.entity.Student;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

}
