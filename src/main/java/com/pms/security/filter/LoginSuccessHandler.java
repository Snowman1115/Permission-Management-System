package com.pms.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pms.common.config.redis.RedisService;
import com.pms.common.result.Result;
import com.pms.common.utils.JwtUtil;
import com.pms.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Login Success Handler
 * @author Sn0w_15
 * @since 24 Jul 2024
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Configure Response Format
        response.setContentType("application/json;charset=utf-8");
        // Get OutPutStream
        ServletOutputStream outputStream = response.getOutputStream();
        // Get Current Login User Info
        User user = (User) authentication.getPrincipal();
        /*User loginUser = new User();
        loginUser.setId(user.getId());
        loginUser.setNickname(user.getNickname());
        loginUser.setAvatar(user.getAvatar());
        loginUser.setAuthorities(user.getAuthorities());*/
        // Generate TOKEN
        String token = jwtUtil.createJWT(JSON.toJSONString(user), null);
        // Convert User into String , Disable Circular Reference Detect
        String result = JSON.toJSONString(Result.success(token), SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        // Store Token Information Store into redis
        String tokenKey = "token_" + token;
        redisService.set(tokenKey,token, 60 * 60 * 1000L);
    }

}
