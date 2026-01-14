package com.example.wisper_one.websocket.chat_group.POJO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: ChatGroupMemberEntity
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */
@Data
public class ChatGroupMemberEntity {
    private Long id;
    private String groupCode;
    private String userCode;
    private String memberNick;
    private Integer role;
    private LocalDateTime joinTime;
    private LocalDateTime muteUntil;
}
