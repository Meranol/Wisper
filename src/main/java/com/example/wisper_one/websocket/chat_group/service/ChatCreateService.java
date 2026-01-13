package com.example.wisper_one.websocket.chat_group.service;

import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupEntity;

/**
 * File: ChatCreateService
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description: 创建群接口
 */
public interface ChatCreateService {

    ChatGroupEntity createChatGroup(ChatGroupEntity chatGroup);

}
