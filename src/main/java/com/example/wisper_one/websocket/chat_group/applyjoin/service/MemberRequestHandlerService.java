package com.example.wisper_one.websocket.chat_group.applyjoin.service;

import com.example.wisper_one.websocket.chat_group.applyjoin.POJO.MemberRequestHandlerDTO;

/**
 * File: MemberRequestHandlerService
 * Author: [周玉诚]
 * Date: 2026/1/25
 * Description:
 */
public interface MemberRequestHandlerService {
    MemberRequestHandlerDTO handle(MemberRequestHandlerDTO entity);
}
