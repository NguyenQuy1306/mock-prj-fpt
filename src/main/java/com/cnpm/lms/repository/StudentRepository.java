package com.cnpm.lms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnpm.lms.model.entity.Instructor;
import com.cnpm.lms.model.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAll(Pageable pageable);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);
}
