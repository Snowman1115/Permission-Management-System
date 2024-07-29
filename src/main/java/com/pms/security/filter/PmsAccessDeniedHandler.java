package com.pms.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pms.common.constants.ResultConstants;
import com.pms.common.result.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * No Permission Handler
 * @author Sn0w_15
 * @since 24 Jul 2024
 */
@Component
public class PmsAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Configure Response Format
        response.setContentType("application/json;charset=utf-8");
        // Get OutPutStream
        ServletOutputStream outputStream = response.getOutputStream();
        // Convert Result into JSON Format
        String result = JSON.toJSONString(Result.error(ResultConstants.NO_AUTH, "UnAuthorized Access"), SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
