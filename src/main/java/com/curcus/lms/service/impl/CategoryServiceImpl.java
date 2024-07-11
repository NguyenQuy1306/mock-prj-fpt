package com.curcus.lms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.entity.Category;

import com.curcus.lms.repository.CategoryRepository;
import com.curcus.lms.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findById(int id) {
        try {
            return ((categoryRepository.findById(id)).orElse(null));
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
}
