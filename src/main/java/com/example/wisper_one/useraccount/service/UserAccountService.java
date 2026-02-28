package com.example.wisper_one.useraccount.service;

import com.example.wisper_one.useraccount.DO.UserAccountDO;

import java.math.BigDecimal;

/**
 * File: UserAccountService
 * Author: [周玉诚]
 * Date: 2026/2/28
 * Description:
 */
public interface UserAccountService {
    // 新增充值方法
    UserAccountDO recharge(BigDecimal amount);
}
