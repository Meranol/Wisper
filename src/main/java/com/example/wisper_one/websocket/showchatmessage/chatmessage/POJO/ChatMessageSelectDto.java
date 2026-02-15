package com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: ChatMessageSelectDto
 * Author: [周玉诚]
 * Date: 2026/2/13
 * Description:
 */
@Data
public class ChatMessageSelectDto {

    private Long id;
    private String sender;
    private String friendCode;
    private String content;
    private String type;
    private Integer readState;
    private Integer revoked;
    private Long lastId;
    private Integer pageSize;
    private LocalDateTime revokedAt;
    private LocalDateTime createTime;




}
