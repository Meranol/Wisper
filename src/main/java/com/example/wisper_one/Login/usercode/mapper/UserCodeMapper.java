package com.example.wisper_one.Login.usercode.mapper;

import com.example.wisper_one.Login.usercode.UserCodeSeq;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * File: UserCodeMapper
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@Mapper
public interface UserCodeMapper {

    UserCodeSeq findByYear(@Param("year") int year);

    int insert(UserCodeSeq seq);

    int update(UserCodeSeq seq);
}
