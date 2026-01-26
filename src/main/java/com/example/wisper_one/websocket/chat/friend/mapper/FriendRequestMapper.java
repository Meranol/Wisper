package com.example.wisper_one.websocket.chat.friend.mapper;

import com.example.wisper_one.websocket.chat.friend.POJO.FriendRequestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * File: FriendRequestMapper
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description:好友申请mapper
 */
@Mapper
public interface FriendRequestMapper {

    int insertFriendRequest(FriendRequestEntity entity);

    int updateStatus(FriendRequestEntity entity);

    FriendRequestEntity selectExistingRequest(@Param("fromUserCode") String fromUserCode,@Param("toUserCode") String toUserCode);


    FriendRequestEntity selectRequest(@Param("fromUserCode") String fromUserCode,@Param("toUserCode") String toUserCode);

}
