package com.example.wisper_one.websocket.chat_group.POJO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: ChatGroupMessageEntity
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */
@Data
public class ChatGroupMessageEntity {
    private Long id;
    private Long groupId;
    private String senderId;
    private String content;
    private String type;
    private LocalDateTime createTime;
}
