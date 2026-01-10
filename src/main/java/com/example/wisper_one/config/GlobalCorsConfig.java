package com.example.wisper_one.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
/**
 * File: GlobalCorsConfig
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@Configuration
@EnableWebSecurity

public class GlobalCorsConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .httpBasic().disable()
                .formLogin().disable();
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // 允许所有域名
        config.setAllowCredentials(true); // 允许携带 Cookie
        config.addAllowedMethod("*"); // 允许所有请求方法
        config.addAllowedHeader("*"); // 允许所有请求头
        config.addExposedHeader("*"); // 暴露所有响应头

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
