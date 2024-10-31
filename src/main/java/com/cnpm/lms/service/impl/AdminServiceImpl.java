package com.cnpm.lms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnpm.lms.exception.ApplicationException;
import com.cnpm.lms.exception.NotFoundException;
import com.cnpm.lms.exception.ValidationException;
import com.cnpm.lms.model.entity.Admin;
import com.cnpm.lms.model.mapper.UserMapper;
import com.cnpm.lms.model.response.AdminResponse;
import com.cnpm.lms.repository.AdminRepository;
import com.cnpm.lms.service.AdminService;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean save(Admin admin) {
        try {
            adminRepository.save(admin);
            return true;
        } catch (ApplicationException a) {
            return false;
        }
    }

    @Override
    public Optional<AdminResponse> findById(Long id) {
        try {
            return adminRepository.findById(id).map(userMapper::toAdminResponse);
        } catch (ApplicationException a) {
            throw a;
        }
    }

    @Override
    public Optional<AdminResponse> findByEmail(String email) {
        try {
            return adminRepository.findByEmail(email).map(userMapper::toAdminResponse);
        } catch (ApplicationException a) {
            throw a;
        }
    }

}
