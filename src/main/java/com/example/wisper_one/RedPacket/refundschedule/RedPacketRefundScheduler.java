//package com.example.wisper_one.RedPacket.refundschedule;
//
//import com.example.wisper_one.RedPacket.POJO.PO.RedPacketPO;
//import com.example.wisper_one.RedPacket.mapper.RedPacketMapper;
//import com.example.wisper_one.useraccount.PO.UserAccountPO;
//import com.example.wisper_one.useraccount.mapper.UserAccountMapper;
//import com.example.wisper_one.utils.Exception.BusinessException;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
///**
// * File: RedPacketRefundScheduler
// * Author: [周玉诚]
// * Date: 2026/2/28
// * Description: 红包定时退回
// */
//@Service
//public class RedPacketRefundScheduler {
//
//    @Resource
//    private StringRedisTemplate redisTemplate;
//
//    @Resource
//    private RedPacketMapper redPacketMapper;
//
//    @Resource
//    private UserAccountMapper accountMapper;
//
//    @Scheduled(fixedRate = 5000)
//    public void refundExpiredRedPackets() {
//        LocalDateTime now = LocalDateTime.now();
//        List<RedPacketPO> expiredPackets = redPacketMapper.selectExpiredPackets(now);
//
//        for (RedPacketPO po : expiredPackets) {
//            if (po.getRemainAmount().compareTo(BigDecimal.ZERO) <= 0) continue;
//
//            refundRedPacket(po);
//        }
//    }
//
//    /**
//     * 乐观锁退回红包
//     */
//    @Transactional
//    public void refundRedPacket(RedPacketPO po) {
//        BigDecimal remainAmount = po.getRemainAmount();
//
//        int updated = redPacketMapper.updateWithVersion(
//                po.getId(),
//                0,
//                BigDecimal.ZERO,
//                po.getVersion()
//        );
//
//        if (updated == 0) return;
//
//
//        // 余额退回
//        UserAccountPO accountPO = accountMapper.selectByUserCode(po.getUserCode());
//        if (accountPO != null) {
//            int accUpdated = accountMapper.updateBalanceWithVersion(
//                    po.getUserCode(),
//                    remainAmount,
//                    accountPO.getVersion()
//            );
//
//            if (accUpdated == 0) {
//                throw new BusinessException("退回账户失败，userCode=" + po.getUserCode());
//
//            }
//        }
//       int a =  accountMapper.insertAccountRecord(
//                po.getUserCode(),
//                remainAmount,              // 正数
//                "红包过期退回",
//                po.getId(),                // 关联红包ID
//                LocalDateTime.now()
//        );
//        if (a == 0) {
//            throw new BusinessException("流水插入失败");
//        }
//        redPacketMapper.updateStatus(po.getId(), (byte) 3);
//    }
//}