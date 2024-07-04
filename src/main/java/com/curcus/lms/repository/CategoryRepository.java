package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curcus.lms.model.entity.Category;

public interface CategoryRepository  extends JpaRepository<Category, Long>{

}
