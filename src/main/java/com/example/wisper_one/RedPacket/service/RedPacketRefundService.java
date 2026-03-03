package com.example.wisper_one.RedPacket.service;

import com.example.wisper_one.RedPacket.POJO.PO.RedPacketPO;
import com.example.wisper_one.RedPacket.mapper.RedPacketMapper;
import com.example.wisper_one.useraccount.PO.UserAccountPO;
import com.example.wisper_one.useraccount.mapper.UserAccountMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * File: RedPacketRefundService
 * Author: [周玉诚]
 * Date: 2026/3/1
 * Description:
 */
@Service
public class RedPacketRefundService {

    @Resource
    private RedPacketMapper redPacketMapper;

    @Resource
    private UserAccountMapper accountMapper;

    @Transactional(rollbackFor = Exception.class)
    public void refundById(Long redPacketId) {

        RedPacketPO po = redPacketMapper.selectById(redPacketId);

        if (po == null) return;

        if (po.getStatus() != 1) return;

        if (po.getRemainAmount().compareTo(BigDecimal.ZERO) <= 0) return;

        BigDecimal remainAmount = po.getRemainAmount();

        // 乐观锁清空库存
        int updated = redPacketMapper.updateWithVersion(
                po.getId(),
                0,
                BigDecimal.ZERO,
                po.getVersion()
        );

        if (updated == 0) return;

        // 余额退回
        UserAccountPO accountPO =
                accountMapper.selectByUserCode(po.getUserCode());

        if (accountPO == null) return;

        int accUpdated = accountMapper.updateBalanceWithVersion(
                po.getUserCode(),
                remainAmount,
                accountPO.getVersion()
        );

        if (accUpdated == 0) {
            throw new BusinessException("退回账户失败");
        }

        accountMapper.insertAccountRecord(
                po.getUserCode(),
                remainAmount,
                "红包过期退回",
                po.getId(),
                LocalDateTime.now()
        );

        redPacketMapper.updateStatus(po.getId(), (byte) 3);
    }
}