package com.curcus.lms.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.Content;
@Repository
public interface ContentRepository extends JpaRepository<Content, Long>{
}
