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

    int updateBalanceWithVersion(@Param("userCode") String userCode,
                                 @Param("amount") BigDecimal amount,
                                 @Param("version") Integer version);

    int insert(UserAccountPO accountPO);

    int insertAccountRecord(@Param("userCode") String userCode,
                            @Param("changeAmount") BigDecimal changeAmount,
                            @Param("type") String type,
                            @Param("relatedId") Long relatedId,
                            @Param("createdAt") java.time.LocalDateTime createdAt);
}
