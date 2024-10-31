package com.cnpm.lms.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cnpm.lms.model.entity.Role;
import com.cnpm.lms.model.entity.User;
import com.cnpm.lms.model.entity.UserDetailsImpl;
import com.cnpm.lms.model.entity.UserRole;
import com.cnpm.lms.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        // Role role = user.getDiscriminatorValue().equals(UserRole.Role.STUDENT) ?
        // Role.STUDENT : Role.INSTRUCTOR;

        Role role = switch (user.getDiscriminatorValue()) {
            case UserRole.Role.STUDENT -> Role.STUDENT;
            case UserRole.Role.ADMIN -> Role.ADMIN;
            case UserRole.Role.INSTRUCTOR -> Role.INSTRUCTOR;
            default -> throw new IllegalArgumentException("Invalid user role");
        };

        return new UserDetailsImpl(user, role);
    }
}
