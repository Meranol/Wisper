package com.example.wisper_one.RedPacket.POJO.PO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * File: RedPacketPO
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description:红包数据库映射实体
 */
@Data
public class RedPacketPO {

    private Long id; // 红包ID（主键）
    private String userCode; // 发红包用户的唯一标识
    private BigDecimal totalAmount; // 红包总金额
    private Integer totalCount; // 红包总个数（拆成多少份）
    private BigDecimal remainAmount; // 剩余金额
    private Integer remainCount; // 剩余个数
    private Integer type; // 红包类型（0=普通红包，1=拼手气红包）
    private Integer status; // 红包状态（0=未抢完，1=已抢完，2=已过期）
    private LocalDateTime expireTime; // 过期时间
    private Integer version; // 版本号（乐观锁控制并发）
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}
