package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.CartItems;
import com.curcus.lms.model.entity.CartItemsId;

import jakarta.transaction.Transactional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, CartItemsId> {
    // CartItems getCartItemByCompositeId(Long cartId, Long CourseId);
    @Modifying
    @Transactional
    @Query("delete from CartItems a where a.id.cartId = :cartId")
    void deleteCartItemsById(@Param("cartId") Long cartId);
}
