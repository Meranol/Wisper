package com.example.wisper_one.RedPacket.POJO.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * File: RedPacketInfoDTO
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description:
 */
@Data
public class RedPacketInfoDTO {
    private Long id;               // 红包ID
    private String userCode;       // 发红包用户
    private BigDecimal totalAmount; // 红包总金额
    private Integer totalCount;    // 红包总个数
    private Integer remainCount;   // 剩余红包个数
    private Integer type;          // 红包类型（普通/拼手气）
    private Integer status;        // 红包状态（未开始/进行中/已结束）
    private LocalDateTime expireTime; // 红包过期时间
}