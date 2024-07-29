package com.pms.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pms.common.constants.ResultConstants;
import com.pms.common.result.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Anonymous User UnAuthorized Access Handler
 * @author Sn0w_15
 * @since 24 Jul 2024
 */
@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Configure Response Format
        response.setContentType("application/json;charset=utf-8");
        // Get OutPutStream
        ServletOutputStream outputStream = response.getOutputStream();
        // Convert Result into JSON Format
        String result = JSON.toJSONString(Result.error(ResultConstants.NO_LOGIN, "Anonymous User Unauthorized Access"), SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}