package com.example.wisper_one.useraccount.mapper;

import com.example.wisper_one.useraccount.PO.UserAccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * File: UserAccountMapper
 * Author: [周玉诚]
 * Date: 2026/2/28
 * Description:
 */
@Mapper
public interface UserAccountMapper {
    UserAccountPO selectByUserCode(String userCode);

    // 乐观锁更新余额
    int updateBalanceWithVersion(@Param("userCode") String userCode,
                                 @Param("amount") BigDecimal amount,
                                 @Param("version") Integer version);

    // 插入账户
    int insert(UserAccountPO accountPO);

    // 插入账户流水
    int insertAccountRecord(@Param("userCode") String userCode,
                            @Param("changeAmount") BigDecimal changeAmount,
                            @Param("type") String type,
                            @Param("relatedId") Long relatedId,
                            @Param("createdAt") java.time.LocalDateTime createdAt);
}
