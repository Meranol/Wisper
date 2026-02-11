//package com.example.wisper_one.websocket.util.config;
//
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//
///**
// * File: RedisKeyspaceConfig
// * Author: [周玉诚]
// * Date: 2026/2/11
// * Description:
// */
//@Configuration
//public class RedisKeyspaceConfig {
//
//    @Bean
//    public ApplicationRunner initRedisConfig(RedisConnectionFactory factory) {
//        return args -> {
//            try (var connection = factory.getConnection()) {
//                connection.setConfig("notify-keyspace-events", "Ex");
//            }
//        };
//    }
//}
