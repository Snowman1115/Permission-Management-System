package com.pms.common.config.security;

import com.pms.security.filter.*;
import com.pms.security.service.PmsUserDetailsService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Spring Security Configuration Class
 * @author Sn0w_15
 * @since 27 Jul 2024
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private PmsAccessDeniedHandler pmsAccessDeniedHandler;
    @Resource
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;
    @Resource
    private PmsUserDetailsService pmsUserDetailsService;
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Login Process Authentication
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // Filter Before Login
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin() // Login Form
                .loginProcessingUrl("/user/login") // Login Page POST URL
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(loginSuccessHandler) // Success Handler
                .failureHandler(loginFailureHandler) // Failure Handler
                .and()
                .csrf().disable() // Disable CSRF
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Disable SESSION
                .and()
                .authorizeHttpRequests() // Configure Request Interceptor
                .antMatchers("/user/login").permitAll() // Permit Login Page
                .anyRequest().authenticated()
                .and()
                .exceptionHandling() // Exception Handler
                .authenticationEntryPoint(anonymousAuthenticationHandler) // Anonymous Authentication Handler
                .accessDeniedHandler(pmsAccessDeniedHandler) // No Permission Handler
                .and()
                .cors();
    }

    /**
     * Configure Authentication Process
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pmsUserDetailsService).passwordEncoder(passwordEncoder());
    }

}
