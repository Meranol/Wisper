package com.example.wisper_one.RedPacket.mapper;

import com.example.wisper_one.RedPacket.POJO.PO.RedPacketPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * File: RedPacketMapper
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description:
 */
@Mapper
public interface RedPacketMapper {

    // 根据红包ID查询红包信息
    RedPacketPO selectById(Long id);

    // 乐观锁更新红包剩余数量和金额
    // 只有 version 匹配时才会更新，防止并发冲突
    int updateWithVersion(@Param("id") Long id,                   // 红包ID
                          @Param("remainCount") Integer remainCount, // 更新后的剩余数量
                          @Param("remainAmount") BigDecimal remainAmount, // 更新后的剩余金额
                          @Param("version") Integer version);       // 当前版本号，用于乐观锁

    int insert(RedPacketPO redPacketPO);


    List<RedPacketPO> selectExpiredPackets(@Param("now") LocalDateTime now);

    // 更新红包状态（例如已结束）
    int updateStatus(@Param("id") Long id, @Param("status") Byte status);
}