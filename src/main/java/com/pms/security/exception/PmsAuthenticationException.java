package com.pms.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Custom Authentication Exception Class (Spring Security)
 */
public class PmsAuthenticationException extends AuthenticationException {

    public PmsAuthenticationException(String message) {
        super(message);
    }

}
