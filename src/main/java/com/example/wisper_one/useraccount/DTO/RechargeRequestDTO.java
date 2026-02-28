package com.example.wisper_one.useraccount.DTO;

import lombok.Data;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * File: RechargeRequestDTO
 * Author: [周玉诚]
 * Date: 2026/2/28
 * Description: 充值请求参数
 */
@Data
public class RechargeRequestDTO {


    @NotNull(message = "充值金额不能为空")
    @DecimalMin(value = "0.01", message = "充值金额必须大于0")
    private BigDecimal amount;

    private String remark; // 充值备注
}