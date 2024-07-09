package com.curcus.lms.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.curcus.lms.model.entity.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {
}