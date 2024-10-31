package com.cnpm.lms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;

import com.cnpm.lms.exception.NotFoundException;
import com.cnpm.lms.exception.UserNotFoundException;
import com.cnpm.lms.model.entity.User;
import com.cnpm.lms.repository.UserRepository;
import com.cnpm.lms.service.PasswordResetService;

@Service
public class PasswordResetImpl implements PasswordResetService {
    // @Autowired
    // private EmailServiceImpl emailService;
    @Autowired
    private VerificationTokenServiceImpl verificationTokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Override
    // public Boolean requestPasswordReset(String email) {
    // try {
    // userRepository.findByEmail(email).orElseThrow(() -> new
    // UserNotFoundException("User Not Found"));
    // String token =
    // verificationTokenService.createVerificationToken(email).orElseThrow();
    // return emailService.sendPasswordResetConfirmation(email, token);
    // } catch (UserNotFoundException e) {
    // return false;
    // }
    // }

    // @Override
    // public Boolean resetPassword(String token) {
    // try {
    // User user = verificationTokenService.validateVerificationToken(token)
    // .orElseThrow(() -> new NotFoundException("Invalid Token"));
    // RandomValueStringGenerator randomValueStringGenerator = new
    // RandomValueStringGenerator(10);
    // String password = randomValueStringGenerator.generate();
    // user.setPassword(passwordEncoder.encode(password));
    // userRepository.save(user);
    // return emailService.sendPassword(user.getEmail(), password);
    // } catch (NotFoundException e) {
    // return false;
    // }
    // }
}
