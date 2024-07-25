package com.pms.exception;

import com.pms.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * GlobalExceptionHandler Class
 * @author Sn0w_15
 * @since 2024-07-24
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Capture Service Error (ServiceException.class)
     * @param serviceError
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException serviceError) {
        return Result.error(serviceError.getCode(), serviceError.getMessage());
    }
}
