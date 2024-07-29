package com.pms.security.filter;

import com.alibaba.fastjson.JSON;
import com.pms.common.config.redis.RedisService;
import com.pms.common.utils.JwtUtil;
import com.pms.entity.User;
import com.pms.security.exception.PmsAuthenticationException;
import com.pms.security.service.PmsUserDetailsService;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Data
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private RedisService redisService;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private PmsUserDetailsService pmsUserDetailsService;
    @Resource
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws  ServletException, IOException {
        try {
            // Get Current URL Address
            String url = request.getRequestURI();
            // Determine Current Request if not login request then verify token
            if (!url.equals("/user/login")) {
                // Validate Token
                this.validateToken(request);
            }
        } catch (AuthenticationException e) {
            loginFailureHandler.onAuthenticationFailure(request,response,e);
        }
        // If it's login request then continue
        doFilter(request, response, filterChain);
    }

    /**
     * Token Verification
     * @param request
     */
    private void validateToken(HttpServletRequest request) {
        // Get Token from header
        String token = request.getHeader("token");
        // if Header don't have token search in parameter
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        // if both don't have token then throw exception
        if (ObjectUtils.isEmpty(token)) {
            throw new PmsAuthenticationException("Missing Token");
        }
        // Determine Token In Redis
        String tokenKey = "token_" + token;
        String redisToken = redisService.get(tokenKey);
        // Check token in Redis, If token is not equal wif token stored in redis
        if (!token.equals(redisToken)) {
            throw new PmsAuthenticationException("Token Expired");
        }
        //  if missing or null then token is expired
        if (ObjectUtils.isEmpty(redisToken)) {
            throw new PmsAuthenticationException("Token Expired");
        }
        // if token is valid then parse token
        Claims claims = jwtUtil.parseJWT(token);
        if (ObjectUtils.isEmpty(claims)) {
            throw new PmsAuthenticationException("Token Parse Fail");
        }
        String userJson = claims.getSubject();
        User user = JSON.parseObject(userJson, User.class);
        // Get UserDetails
        UserDetails userDetails = pmsUserDetailsService.loadUserByUsername(user.getUsername());
        if (userDetails == null) {
            throw new PmsAuthenticationException("Token Authentication Fail");
        }
        // Create User Identification Object
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // Configure Request Info
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
