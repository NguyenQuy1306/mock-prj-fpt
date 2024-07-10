package com.curcus.lms.service.impl;

import com.curcus.lms.service.EmailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private VerificationTokenServiceImpl verificationTokenServiceImpl;

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

    @Override
    public Boolean sendEmailToStudent(String to) {
        try {
            String token = verificationTokenServiceImpl.createVerificationToken(to)
                    .orElseThrow(() -> new IllegalArgumentException("Token generation failed"));
            // Create email content
            String subject = "Xác nhận địa chỉ email của bạn";
            String template = String.format("<p>Dear %s,</p>"
                    + "<p>Để xác thực địa chỉ email đã đăng ký vui lòng ấn "
                    + "<a href=\"http://localhost:8080/api/v1/auth/is-expired-verification?token=%s\">vào đây</a>.</p>"
                    + "<p>Best regards,</p>"
                    + "<p>FSA Backend</p>", to, token);

            return sendEmail(to, subject, template);
        } catch (RuntimeException r) {
            r.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean sendEmailToInstructor(String to) {
        try {
            // Create email content
            String subject = "Chúc mừng bạn đã trở thành giáo viên tại Cursus.";
            String template = String.format("<p>Dear %s,</p>"
                    + "<p>Chúc mừng bạn đã trở thành giáo viên tại Cursus. " +
                    "Hãy truy cập vào hệ thống để tiến hành cung cấp," +
                    "phát triển khóa học của bạn.</p>"
                    + "<p>Best regards,</p>"
                    + "<p>FSA Backend</p>", to);

            return sendEmail(to, subject, template);
        } catch (RuntimeException r) {
            r.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean sendPasswordResetConfirmation(String to, String token) {
        try {
            String subject = "Khôi phục mật khẩu";
            String template = String.format(
                      "<p>Dear %s,</p>"
                    + "<p>Vui lòng nhấn <a href=\"http://localhost:8080/password-reset/reset?token=%s\">vào đây</a> để khôi phục tài khoản của bạn</p>"
                    + "<p>Best regards,</p>"
                    + "<p>FSA Backend</p>"
                    , to, token);
            return sendEmail(to, subject, template);
        } catch(Exception e) {
            return false;
        }
    }
}
