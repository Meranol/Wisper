package com.example.wisper_one.RedPacket.mapper;

import com.example.wisper_one.RedPacket.POJO.PO.RedPacketRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * File: RedPacketRecordMapper
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description:
 */
@Mapper
public interface RedPacketRecordMapper {

    // 插入一条抢红包记录
    int insert(RedPacketRecordPO record);
    // record 包含：红包ID、用户ID、抢到金额、时间等字段

    // 判断用户是否已经抢过指定红包
    boolean exists(@Param("redPacketId") Long redPacketId,  // 红包ID
                   @Param("userCode") String userCode);    // 用户标识
}