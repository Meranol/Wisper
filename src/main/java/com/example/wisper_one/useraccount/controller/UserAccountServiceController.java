package com.example.wisper_one.useraccount.controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.useraccount.DO.UserAccountDO;
import com.example.wisper_one.useraccount.DTO.RechargeRequestDTO;
import com.example.wisper_one.useraccount.service.UserAccountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * File: UserAccountServiceController
 * Author: [周玉诚]
 * Date: 2026/2/28
 * Description: 用户账户控制器
 */
@RestController
@RequestMapping("/user/account")
public class UserAccountServiceController {

    @Resource
    private UserAccountService userAccountService;

    @PostMapping("/add")
    public Result<?> add(@RequestBody @Valid UserAccountDO userAccountDO) {
        // 这里是原来的添加账户接口
        return Result.success();
    }

    /**
     * 充值接口
     * @param request 充值请求参数
     * @return 充值后的账户信息
     */
    @PostMapping("/recharge")
    public Result<UserAccountDO> recharge(@RequestBody @Valid RechargeRequestDTO request) {
        try {
            UserAccountDO accountDO = userAccountService.recharge(
                    request.getAmount()
            );
            return Result.success(accountDO);
        } catch (Exception e) {
            return Result.failure(500, e.getMessage());
        }
    }
}