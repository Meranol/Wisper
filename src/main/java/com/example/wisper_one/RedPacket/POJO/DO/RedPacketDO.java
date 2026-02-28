package com.example.wisper_one.RedPacket.POJO.DO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * File: RedPacketDO
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description:红包领域对象
 */
@Data
public class RedPacketDO {
    private Long id; // 红包ID（主键）
    private String userCode; // 发红包用户唯一标识
    private BigDecimal totalAmount; // 红包总金额
    private Integer totalCount; // 红包总数量
    private BigDecimal remainAmount; // 剩余金额
    private Integer remainCount; // 剩余数量
    private Integer type; // 红包类型（0=普通红包，1=拼手气红包）
    private Integer status; // 红包状态（0=进行中，1=已抢完，2=已过期）
    private LocalDateTime expireTime; // 红包过期时间
    private Integer version; // 乐观锁版本号（用于并发控制）

    /**
     * 判断红包是否可以被抢
     */
    public boolean canGrab() {
        return status == 1 && remainCount > 0 && expireTime.isAfter(LocalDateTime.now());
    }
}