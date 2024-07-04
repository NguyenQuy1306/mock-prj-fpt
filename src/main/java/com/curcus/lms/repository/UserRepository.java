package com.curcus.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curcus.lms.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
