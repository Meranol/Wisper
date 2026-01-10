package com.example.wisper_one.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * File: SecurityConfig
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@Configuration
public class SecurityConfig { //用户密码哈希值加密
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
