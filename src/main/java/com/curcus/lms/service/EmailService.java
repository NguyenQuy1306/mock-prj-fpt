package com.curcus.lms.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    Boolean sendEmail(String to, String subject, String body);
}
