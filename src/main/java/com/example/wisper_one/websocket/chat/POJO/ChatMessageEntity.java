package com.example.wisper_one.websocket.chat.POJO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: ChatMessageEntity
 * Author: [周玉诚]
 * Date: 2026/1/12
 * Description:
 */

@Data
public class ChatMessageEntity {
    private Long id;

    /** 发送者 userId */
    private String sender;

    /** 接收者 userId */
    private String receiver;

    /** 消息内容 */
    private String content;

    /** 是否撤回：0-正常，1-已撤回 */
    private Integer revoked;

    private String type;    // text / image

    /** 撤回时间 */
    private LocalDateTime revokedAt;

    /** 创建时间 */
    private LocalDateTime createTime;
}
