package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curcus.lms.model.entity.Instructor;

import org.springframework.stereotype.Repository;


@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long>{

    boolean existsByName(String name);
}