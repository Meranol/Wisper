package com.example.wisper_one.websocket.chat_group.applyjoin.POJO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: MemberRequestHandlerEntity
 * Author: [周玉诚]
 * Date: 2026/1/25
 * Description:
 */
@Data
public class MemberRequestHandlerDTO {

    /** 申请ID（前端传） */
    private Long id;

    /** 处理结果：1 同意，2 拒绝 */
    private Integer status;

    /** 处理人（后端填） */
    private String handlername;

    /** 处理时间（后端填） */
    private LocalDateTime handleTime;
}
