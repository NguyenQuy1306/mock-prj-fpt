package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.entity.CartItemsId;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, CartItemsId> {
    // CartItems getCartItemByCompositeId(Long cartId, Long CourseId);
}
