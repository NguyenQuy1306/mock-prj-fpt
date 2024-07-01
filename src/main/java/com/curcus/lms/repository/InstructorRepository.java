package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curcus.lms.model.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long>{

    
}