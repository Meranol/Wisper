//package com.example.wisper_one.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.annotation.Resource;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Resource
//    private JwtAuthInterceptor jwtAuthInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtAuthInterceptor)
//                .addPathPatterns("/**")  // 拦截所有请求
//                .excludePathPatterns("/user/login", "/user/register");  // 排除登录注册
//    }
//}