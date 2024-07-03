package com.curcus.lms.service.impl;

import com.curcus.lms.model.entity.Role;
import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.entity.UserDetailsImpl;
import com.curcus.lms.model.entity.UserRole;
import com.curcus.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        Role role = user.getDicriminatorValue().equals(UserRole.Role.STUDENT) ? Role.STUDENT : Role.INSTRUCTOR;
        return new UserDetailsImpl(user, role);
    }
}
