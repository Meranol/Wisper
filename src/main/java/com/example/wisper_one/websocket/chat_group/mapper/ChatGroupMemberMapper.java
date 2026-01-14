package com.example.wisper_one.websocket.chat_group.mapper;

import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * File: ChatGroupMember
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */
@Mapper
public interface ChatGroupMemberMapper {
    int insert(ChatGroupMemberEntity record);
}
