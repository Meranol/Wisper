package com.example.wisper_one.websocket.chat.friend.POJO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: FriendRelationEntity
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description: 好友关系表，此表为单向
 */
@Data
public class FriendRelationEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 自己的用户标识
     */
    private String userCode;

    /**
     * 好友的用户标识
     */
    private String friendCode;


    /**
     * 好友状态
     * 1：正常
     * 2：拉黑
     */
    private Integer status;

    /**
     * 成为好友的时间
     */
    private LocalDateTime createTime;

}
