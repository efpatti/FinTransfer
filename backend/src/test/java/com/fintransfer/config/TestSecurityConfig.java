package com.fintransfer.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestSecurityConfig {
    @Bean
    public com.fintransfer.security.JwtUtil jwtUtil() {
        return new com.fintransfer.security.JwtUtil();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests().anyRequest().permitAll();
        return http.build();
    }
}
