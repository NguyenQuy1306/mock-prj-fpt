package com.curcus.lms.email;

import com.curcus.lms.model.entity.VerificationToken;
import com.curcus.lms.repository.VerificationTokenRepository;
import com.curcus.lms.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
}