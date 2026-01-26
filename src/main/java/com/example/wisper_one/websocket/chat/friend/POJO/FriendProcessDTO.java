package com.example.wisper_one.websocket.chat.friend.POJO;

import lombok.Data;

/**
 * File: FriendProcessEntity
 * Author: [周玉诚]
 * Date: 2026/1/23
 * Description:
 */
@Data
public class FriendProcessDTO {

    private String fromUserCode;
    /**
     * 1 同意
     * 2 拒绝
     */
    private Integer status;
}
