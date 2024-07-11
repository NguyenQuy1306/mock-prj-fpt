package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curcus.lms.model.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByStudent_UserId(Long studentId);
}
