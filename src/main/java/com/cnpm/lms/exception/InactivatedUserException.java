package com.cnpm.lms.exception;

public class InactivatedUserException extends RuntimeException {
    public InactivatedUserException(String message) {
        super(message);
    }
}
