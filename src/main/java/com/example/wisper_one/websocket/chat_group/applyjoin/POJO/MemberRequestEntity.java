package com.example.wisper_one.websocket.chat_group.applyjoin.POJO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: MemberProcessPoJo
 * Author: [周玉诚]
 * Date: 2026/1/24
 * Description:CREATE TABLE member_request (
 *     id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
 *
 *     from_user_code VARCHAR(50) NOT NULL COMMENT '申请人用户编码',
 *
 *     to_group VARCHAR(64) NOT NULL COMMENT '目标群聊ID/群编码',
 *
 *     status TINYINT NOT NULL DEFAULT 0 COMMENT '申请状态：0待处理 1同意 2拒绝',
 *
 *     create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
 *
 *     remark VARCHAR(255) DEFAULT NULL COMMENT '申请理由',
 *
 *     handlername VARCHAR(50) DEFAULT NULL COMMENT '处理人用户名',
 *
 *     handle_time DATETIME DEFAULT NULL COMMENT '处理时间（同意/拒绝）',
 *
 *     -- 防止重复申请（同一用户对同一群只能有一条待处理）
 *     UNIQUE KEY uk_request (from_user_code, to_group),
 *
 *     -- 查询优化索引
 *     INDEX idx_to_group (to_group),
 *     INDEX idx_status (status),
 *     INDEX idx_create_time (create_time)
 *
 *  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群聊入群申请表';
 */
@Data
public class MemberRequestEntity {

    private Long id;

    /**
     * 申请人
     */
    private String fromUserCode;

    /**
     * 目标群聊
     */
    private String toGroup;
    /**
     * 申请状态
     * 0：待处理
     * 1：同意
     * 2：拒绝
     */
    private Integer status;

    /**
     * 申请时间
     */
    private LocalDateTime createTime;
    /**
     * 申请理由
     */
    private String remark;
    /**
     * 处理人
     */
    private String handlername;
    /**
     * 处理时间（同意/拒绝）
     */
    private LocalDateTime handleTime;

}
