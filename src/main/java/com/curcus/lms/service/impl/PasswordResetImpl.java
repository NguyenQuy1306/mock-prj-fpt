package com.curcus.lms.service.impl;

import com.curcus.lms.model.entity.User;
import com.curcus.lms.repository.UserRepository;
import com.curcus.lms.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetImpl implements PasswordResetService {
    @Autowired
    private  EmailServiceImpl emailService;

    @Autowired
    private VerificationTokenServiceImpl verificationTokenService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Boolean requestPasswordReset(String email) {
        // check if user's email exist
        try {
            userRepository.findByEmail(email).orElseThrow();
            String token = verificationTokenService.createVerificationToken(email).orElseThrow();
            String body = "<p>Dear " + email + ",</p>"
                    + "<p>Vui lòng nhấn vào <a href=\"http://localhost:8080/password-reset/reset?token=" + token + "\">đây</a> để nhận mật khẩu mới</p>";
            emailService.sendEmail(email, "Xác nhận email", body);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean resetPassword(String token) {
        try {
            User user = verificationTokenService.validateVerificationToken(token).orElseThrow();
            RandomValueStringGenerator randomValueStringGenerator = new RandomValueStringGenerator(10);
            String password = randomValueStringGenerator.generate();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            String body = "<p>Dear " + user.getEmail() + ",</p>"
                    + "<p>Mật khẩu mới của bạn là: " + password + "</p>";
            emailService.sendEmail(user.getEmail(), "Reset mật khẩu", body);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
