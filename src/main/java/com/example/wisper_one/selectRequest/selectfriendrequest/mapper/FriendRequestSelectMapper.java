package com.example.wisper_one.selectRequest.selectfriendrequest.mapper;

import com.example.wisper_one.websocket.chat.friend.POJO.FriendRequestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * File: FriendRequestSelectMapper
 * Author: [周玉诚]
 * Date: 2026/2/24
 * Description:
 */
@Mapper
public interface FriendRequestSelectMapper {
    List<FriendRequestEntity> friendRequestEntityList(@Param("userCode") String userCode);
}
