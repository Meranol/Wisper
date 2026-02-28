package com.example.wisper_one.useraccount.DO;

import lombok.Data;
import java.math.BigDecimal;

/**
 * File: UserAccountDO
 * Author: [周玉诚]
 * Date: 2026/2/28
 * Description: 账户业务对象，用于封装业务逻辑
 */
@Data
public class UserAccountDO {
    private Long id;
    private String userCode;
    private BigDecimal balance;
    private Integer version;

    /**
     * 判断账户余额是否足够
     */
    public boolean hasEnoughBalance(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    /**
     * 增加余额
     */
    public void addBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    /**
     * 扣减余额
     */
    public void subtractBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}