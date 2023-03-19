package com.socaly.auth;

public class AuthException extends RuntimeException {
    public AuthException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
