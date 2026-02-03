package com.example.wisper_one.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * File: CorsConfig
 * Author: [周玉诚]
 * Date: 2026/2/2
 * Description:
 */
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsFilter() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 所有接口都允许跨域
                        .allowedOrigins("http://localhost:5173") // 允许的前端地址
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true); // 如果需要传 Cookie
            }
        };
    }
}
