package com.example.wisper_one.Login.DTO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * File: UserAccountDTO
 * Author: [周玉诚]
 * Date: 2026/2/28
 * Description: 用于接口传输的对象
 */
@Data
public class UserAccountDTO {
    private String userCode;    // 用户唯一标识
    private BigDecimal balance; // 当前余额
}