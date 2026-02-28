package com.example.wisper_one.useraccount.DTO;
import lombok.Data;
import java.math.BigDecimal;

/**
 * File: UserAccountDTO
 * Author: [周玉诚]
 * Date: 2026/2/28
 * Description: 对外接口返回账户信息
 */
@Data
public class UserAccountDTO {
    private String userCode;
    private BigDecimal balance; // 当前余额
}