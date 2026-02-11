package com.example.wisper_one.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * File: WebMvcConfig
 * Author: [周玉诚]
 * Date: 2026/1/12
 * Description:“资源走 Handler，拦截走 Interceptor，消息走 Converter，跨域走 Cors”
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 我将/uploads/images/** 映射到 E:/wisperimage/

        //用法http://localhost:8089/uploads/images/c2956cbf368c4e9ca74946c377eae56b.jpg
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:E:/wisperimage/");
    }
}
