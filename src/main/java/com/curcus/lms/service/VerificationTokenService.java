package com.curcus.lms.service;

import java.util.Optional;

public interface VerificationTokenService {
    Optional<String> createVerificationToken(String email);
}
