package com.pms.security.filter;

import com.alibaba.fastjson.JSON;
import com.pms.common.constants.ResultConstants;
import com.pms.common.result.Result;
import com.pms.security.exception.PmsAuthenticationException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Login Failure Handler
 * @author Sn0w_15
 * @since 24 Jul 2024
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // Configure Response Format
        response.setContentType("application/json;charset=utf-8");
        // Get OutPutStream
        ServletOutputStream outputStream = response.getOutputStream();
        // Define Error Message
        String message = null;
        // Determine Exception Error
        if (exception instanceof AccountExpiredException) {
            message = "Login Fail, Account is Expired.";
        } else if (exception instanceof BadCredentialsException) {
            message = "Login Fail, Username Or Password Incorrect.";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "Login Fail, Credentials Expired.";
        } else if (exception instanceof DisabledException) {
            message = "Login Fail, Account is Disabled.";
        } else if (exception instanceof LockedException) {
            message = "Login Fail, Account is Locked.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = "Login Fail, Account Incorrect.";
        } else if (exception instanceof PmsAuthenticationException) {
            message = exception.getMessage();
        } else {
            message = "Login Fail";
        }
        // Convert Result into JSON Format
        String result = JSON.toJSONString(Result.error(ResultConstants.INTERNAL_SERVER_ERROR, message));
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
