package com.example.wisper_one.websocket.showchatmessage.chatmessage.Service;

import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.GroupChatMessageSelectDto;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.GroupChatMessageSelectVo;

import java.util.List;

/**
 * File: GroupChatMessageSelectService
 * Author: [周玉诚]
 * Date: 2026/2/14
 * Description:
 */
public interface GroupChatMessageSelectService {
    List<GroupChatMessageSelectVo> getGroupMessages(GroupChatMessageSelectDto dto);

}
