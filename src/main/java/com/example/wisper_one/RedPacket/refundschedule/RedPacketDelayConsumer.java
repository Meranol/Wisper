package com.example.wisper_one.RedPacket.refundschedule;

import com.example.wisper_one.RedPacket.service.RedPacketRefundService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * File: RedPacketDelayConsumer
 * Author: [周玉诚]
 * Date: 2026/3/1
 * Description:
 */
@Service
public class RedPacketDelayConsumer {

    @Resource
    private StringRedisTemplate redisTemplates;

    @Resource
    private RedPacketRefundService refundService;

    @Scheduled(fixedRate = 1000)
    public void handleExpiredRedPacket() {

        long now = System.currentTimeMillis();

        Set<String> ids = redisTemplates.opsForZSet()
                .rangeByScore("red_packet:delay", 0, now);

        if (ids == null || ids.isEmpty()) return;

        for (String id : ids) {

            Long redPacketId = Long.valueOf(id);

            try {
                refundService.refundById(redPacketId);

                // 删除已处理任务
                redisTemplates.opsForZSet()
                        .remove("red_packet:delay", id);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}