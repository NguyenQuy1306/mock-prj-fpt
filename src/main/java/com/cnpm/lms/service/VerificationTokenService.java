package com.cnpm.lms.service;

import java.util.Optional;

import com.cnpm.lms.model.entity.User;

public interface VerificationTokenService {
    Optional<String> createVerificationToken(String email);

    Optional<User> validateVerificationToken(String token);

    void revokePreviousTokens(Long userId);
}
