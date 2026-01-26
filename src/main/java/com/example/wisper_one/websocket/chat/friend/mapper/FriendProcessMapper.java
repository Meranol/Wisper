package com.example.wisper_one.websocket.chat.friend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * File: FriendProcessMapper
 * Author: [周玉诚]
 * Date: 2026/1/23
 * Description:
 */
@Mapper
public interface FriendProcessMapper {

    int acceptfriendrequest (@Param("status") int status);


}
