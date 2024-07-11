package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.curcus.lms.model.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select a from Cart a join a.student b where b.userId = :studentId")
    Cart getCartNotPaidByStudentId(@Param("studentId") Long studentId);
}
