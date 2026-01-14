package com.example.wisper_one.websocket.chat_group.service;

import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMapper;

/**
 * File: ChatGroupJoinService
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */
public interface ChatGroupJoinService {
    ChatGroupMemberEntity join(ChatGroupMemberEntity chatGroupMemberEntity);
}
