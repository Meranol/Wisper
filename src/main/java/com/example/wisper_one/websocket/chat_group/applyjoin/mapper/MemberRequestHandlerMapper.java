package com.example.wisper_one.websocket.chat_group.applyjoin.mapper;

import com.example.wisper_one.websocket.chat_group.applyjoin.POJO.MemberRequestHandlerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * File: MemberRequestHandlerMapper
 * Author: [周玉诚]
 * Date: 2026/1/25
 * Description:
 */
@Mapper
public interface MemberRequestHandlerMapper {

    int updateById(MemberRequestHandlerDTO dto);

    Integer selectStatusById(@Param("id") Long id);
    String selectucodeById(@Param("id") Long id);

    String selectGroupCodeById(Long id);


}
