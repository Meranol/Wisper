package com.example.wisper_one.websocket.chat_group.mapper;

import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupEntity;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * File: ChatGroupMapper
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */
@Mapper
public interface ChatGroupMapper {

    /**
     * 查询某个群内的指定成员
     */
    ChatGroupMemberEntity selectMember(@Param("groupCode") String groupId, @Param("userId") String userId);

    /**
     * 查询群所有成员
     */
    List<ChatGroupMemberEntity> selectMembers(@Param("groupCode") String groupId);

    ChatGroupEntity selectByGroupCode(@Param("groupCode") String groupCode);

}