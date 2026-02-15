package com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: GroupChatMessageSelectVo
 * Author: [周玉诚]
 * Date: 2026/2/13
 * Description:群聊消息查询Mapper接口
 */
@Data
public class GroupChatMessageSelectVo {
    private Long id;
    private String sender;
    private String groupCode;
    private String content;
    private String type;
    private Integer recalled;
    private String recalledBy;
    private LocalDateTime revokedAt;
    private LocalDateTime createTime;
}
