package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.curcus.lms.model.entity.Cart;

import jakarta.transaction.Transactional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select a from Cart a join a.student b where b.userId = :studentId")
    Cart getCartNotPaidByStudentId(@Param("studentId") Long studentId);
    
    @Query("select a from Cart a join a.student b where b.userId = :studentId  order by b.id desc limit 1")
    Cart getCartByStudentId(@Param("studentId") Long studentId);

    // @Modifying
    // @Transactional
    // @Query("delete from Cart a where a.id.studentid = :studentId")
    // void deleteStudentCart(@Param("studentId") Long studentId);
}
