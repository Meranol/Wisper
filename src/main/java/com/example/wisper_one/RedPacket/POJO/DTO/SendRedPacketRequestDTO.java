package com.example.wisper_one.RedPacket.POJO.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;




/**
 * File: SendRedPacketRequestDTO
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description:发红包请求 DTO
 */

@Data
public class SendRedPacketRequestDTO {
    private BigDecimal totalAmount;  // 红包总金额
    private Integer totalCount;      // 红包总数量
    private Integer type;            // 1=普通红包, 2=拼手气红包
    private LocalDateTime expireTime;// 过期时间
}