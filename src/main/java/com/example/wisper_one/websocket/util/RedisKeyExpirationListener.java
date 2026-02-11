//package com.example.wisper_one.websocket.util;
//
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//
///**
// * File: RedisKeyExpirationListener
// * Author: [周玉诚]
// * Date: 2026/2/11
// * Description: Spring 监听 Redis 过期事件
// */
//@Component
//public class RedisKeyExpirationListener implements MessageListener {
//
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        String expiredKey = new String(message.getBody());
//
//        if (expiredKey.startsWith("login:token:")) {
//
//            String userCode = expiredKey.replace("login:token:", "");
//
//            System.out.println("Redis过期监听触发，踢下线：" + userCode);
//
//            GlobalWsSessionManager.kick(
//                    userCode,
//                    CloseStatus.NOT_ACCEPTABLE.withReason("token expired")
//            );
//        }
//    }
//}
