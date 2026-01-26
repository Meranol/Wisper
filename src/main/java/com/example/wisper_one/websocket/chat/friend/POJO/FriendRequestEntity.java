package com.example.wisper_one.websocket.chat.friend.POJO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: FriendRequestEntity
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description:好友申请
 */
@Data
public class FriendRequestEntity {


    /**
     * 主键
     */
    private Long id;

    /**
     * 申请人
     */
    private String fromUserCode;

    /**
     * 被申请人
     */
    private String toUserCode;

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
     * 处理时间（同意/拒绝）
     */
    private LocalDateTime handleTime;


}
