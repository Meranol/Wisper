package com.example.wisper_one.useraccount.service.servicelmpl;

import com.example.wisper_one.Login.POJO.UserPo;
import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.useraccount.DO.UserAccountDO;
import com.example.wisper_one.useraccount.PO.UserAccountPO;
import com.example.wisper_one.useraccount.mapper.UserAccountMapper;
import com.example.wisper_one.useraccount.service.UserAccountService;
import com.example.wisper_one.utils.Exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * File: UserAccountServicelmpl
 * Author: [周玉诚]
 * Date: 2026/2/28
 * Description:
 */
@Service
public class UserAccountServicelmpl implements UserAccountService {

    @Resource
    private UserAccountMapper userAccountMapper;


    @Resource
    private UserMapper userMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserAccountDO recharge(BigDecimal amount) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String userCode = userMapper.selectCodeByUname(name);

        UserAccountPO accountPO = userAccountMapper.selectByUserCode(userCode);
        if (accountPO == null) {
            throw new RuntimeException("账户不存在");
        }

        int updateCount = userAccountMapper.updateBalanceWithVersion(
                userCode,
                amount,
                accountPO.getVersion()
        );
        if (updateCount == 0) {
            throw new RuntimeException("充值失败");
        }
        userAccountMapper.insertAccountRecord(
                userCode,
                amount,
                "用户充值",
                null,
                LocalDateTime.now()
        );
        UserAccountPO updatedAccount = userAccountMapper.selectByUserCode(userCode);
        UserAccountDO userAccountDO = new UserAccountDO();
        BeanUtils.copyProperties(updatedAccount, userAccountDO);
        return userAccountDO;
    }
}