package com.example.wisper_one.RedPacket.POJO.DTO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * File: GrabRedPacketResponseDTO
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description:
 */
@Data
public class GrabRedPacketResponseDTO {
    private boolean success;     // 是否抢红包成功
    private BigDecimal amount;   // 抢到的红包金额
    private String msg;          // 提示信息（手速慢了/没抢到）
}