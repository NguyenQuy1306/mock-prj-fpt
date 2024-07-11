package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curcus.lms.model.entity.OrderItems;
import com.curcus.lms.model.entity.OrderItemsId;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, OrderItemsId> {

}
