package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curcus.lms.model.entity.CartItems;

import java.util.List;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    List<CartItems> findAllByCart_CartId(Long cartId);
}
