package com.example.wisper_one.websocket.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;

import javax.annotation.Resource;

/**
 * File: WsTokenCheckTask
 * Author: [周玉诚]
 * Date: 2026/2/11
 * Description:
 */
@Component
public class WsRedisTokenCheck {
    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Scheduled(fixedDelay = 60_000)
    public void checkOnlineToken() {

        for (String userCode : GlobalWsSessionManager.getOnlineUserCodes()) {
            String redisKey = "login:token:" + userCode;
            System.out.println(redisTemplate.opsForValue().get(redisKey)+"123");
            if (!Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
                GlobalWsSessionManager.kick(
                        userCode,
                        CloseStatus.NOT_ACCEPTABLE.withReason("token expired")
                );
                System.out.println("token过期，踢下线：" + userCode);
            }
        }
    }
}
