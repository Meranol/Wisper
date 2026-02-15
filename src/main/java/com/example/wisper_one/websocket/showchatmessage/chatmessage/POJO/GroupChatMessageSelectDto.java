package com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO;

import lombok.Data;

/**
 * File: GroupChatMessageSelectDto
 * Author: [周玉诚]
 * Date: 2026/2/13
 * Description:
 */
@Data
public class GroupChatMessageSelectDto {
    private String groupCode;
    private String userCode;
    private Long lastId;
    private Integer pageSize;
    private String message;
}
