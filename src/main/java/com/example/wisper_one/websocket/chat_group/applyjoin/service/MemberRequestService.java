package com.example.wisper_one.websocket.chat_group.applyjoin.service;

import com.example.wisper_one.websocket.chat_group.applyjoin.POJO.MemberRequestEntity;

/**
 * File: MemberProcessService
 * Author: [周玉诚]
 * Date: 2026/1/24
 * Description:
 */
public interface MemberRequestService {
    MemberRequestEntity join(MemberRequestEntity memberRequestPoJo);
}
