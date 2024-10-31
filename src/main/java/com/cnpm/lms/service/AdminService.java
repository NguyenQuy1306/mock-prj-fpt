package com.cnpm.lms.service;

import java.util.Optional;

import com.cnpm.lms.model.entity.Admin;
import com.cnpm.lms.model.response.AdminResponse;

public interface AdminService {
    Boolean save(Admin admin);

    Optional<AdminResponse> findById(Long id);

    Optional<AdminResponse> findByEmail(String email);
}
