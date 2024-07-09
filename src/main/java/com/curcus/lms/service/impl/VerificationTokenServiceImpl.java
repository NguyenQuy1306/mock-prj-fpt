package com.curcus.lms.service.impl;

import com.curcus.lms.model.entity.VerificationToken;
import com.curcus.lms.repository.UserRepository;
import com.curcus.lms.repository.VerificationTokenRepository;
import com.curcus.lms.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Override
    public Optional<String> createVerificationToken(String email) {
        // create Token
        RandomValueStringGenerator stringGenerator = new RandomValueStringGenerator(24);
        String token = stringGenerator.generate();
        try {
            LocalDateTime timeNow = LocalDateTime.now();
            VerificationToken verificationToken = VerificationToken.builder()
                    .token(token)
                    .issueAt(timeNow)
                    .revoked(false)
                    .user(userRepository.findByEmail(email).orElseThrow())
                    .build();
            verificationTokenRepository.save(verificationToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(token);
    }
}
