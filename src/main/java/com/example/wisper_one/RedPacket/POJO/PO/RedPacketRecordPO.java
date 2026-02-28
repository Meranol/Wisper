package com.example.wisper_one.RedPacket.POJO.PO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * File: RedPacketRecordPO
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description:
 */
@Data
public class RedPacketRecordPO {
    private Long id;
    private Long redPacketId;
    private String userCode;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
