package com.example.wisper_one.websocket.showchatmessage.chatmessage.Service;

import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.ChatMessageSelectDto;

import java.util.List;

/**
 * File: ChatMessageSelectService
 * Author: [周玉诚]
 * Date: 2026/2/13
 * Description:
 */
public interface ChatMessageSelectService {

        List<ChatMessageSelectDto> getChatMessageSelectService(ChatMessageSelectDto dto);



}
