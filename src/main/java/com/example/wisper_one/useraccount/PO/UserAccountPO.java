package com.example.wisper_one.useraccount.PO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * File: UserAccountPO
 * Author: [周玉诚]
 * Date: 2026/2/28
 * Description: 用户账户表映射实体
 */
@Data
public class UserAccountPO {
    private Long id;
    private String userCode;       // 对应 users.public_id
    private BigDecimal balance;    // 账户余额
    private Integer version;       // 乐观锁
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}