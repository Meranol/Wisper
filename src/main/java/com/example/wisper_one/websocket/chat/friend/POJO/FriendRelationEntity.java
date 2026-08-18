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
     * 2：拉黑（单方可解除）
     * 3：已删除（双方解除，需重新申请）
     */
    private Integer status;

    /**
     * 拉黑操作人（谁拉黑谁），正常状态为 null
     */
    private String operatorCode;

    /**
     * 成为好友的时间
     */
    private LocalDateTime createTime;

}
