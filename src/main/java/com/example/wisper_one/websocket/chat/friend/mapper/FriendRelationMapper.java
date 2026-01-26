package com.example.wisper_one.websocket.chat.friend.mapper;

import com.example.wisper_one.websocket.chat.friend.POJO.FriendRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * File: FriendRelationMapper
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description:好友关系Mapper
 */
@Mapper
public interface FriendRelationMapper {
    int insertFriendRequest (FriendRelationEntity friendRelationEntity);

}
