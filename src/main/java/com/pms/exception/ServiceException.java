package com.pms.exception;

import lombok.Getter;

/**
 * Custom Service Exception
 * @author Sn0w_15
 * @since 2024-07-24
 */
@Getter
public class ServiceException extends RuntimeException {
    private Integer code;
    /**
     * Service Exception
     * @param code
     * @param message
     */
    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
