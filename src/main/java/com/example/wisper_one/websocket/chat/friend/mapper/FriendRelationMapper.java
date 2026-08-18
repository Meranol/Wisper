package com.example.wisper_one.websocket.chat.friend.mapper;

import com.example.wisper_one.websocket.chat.friend.POJO.FriendRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * File: FriendRelationMapper
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description:好友关系Mapper
 */
@Mapper
public interface FriendRelationMapper {
    int insertFriendRequest (FriendRelationEntity friendRelationEntity);

    int updateFriendStatus(@Param("userCode") String userCode, @Param("friendCode") String friendCode, @Param("status") int status, @Param("operatorCode") String operatorCode);

    Integer selectFriendStatus(@Param("userCode") String userCode, @Param("friendCode") String friendCode);

    FriendRelationEntity selectRelation(@Param("userCode") String userCode, @Param("friendCode") String friendCode);

}
