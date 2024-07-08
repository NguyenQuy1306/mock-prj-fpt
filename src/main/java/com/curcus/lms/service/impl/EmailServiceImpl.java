package com.curcus.lms.service.impl;

import com.curcus.lms.service.EmailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.mail.*;
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailProperties mailProperties;

    @Override
    public Boolean sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress(mailProperties.getUsername()));
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
