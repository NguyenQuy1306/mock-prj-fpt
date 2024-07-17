package com.curcus.lms.service;

public interface PasswordResetService {
    public Boolean requestPasswordReset(String email);
    public Boolean resetPassword(String token);
}
