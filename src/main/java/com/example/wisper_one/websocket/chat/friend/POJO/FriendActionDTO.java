package com.example.wisper_one.websocket.chat.friend.POJO;

import lombok.Data;

/**
 * File: FriendActionDTO
 * Author: [周玉诚]
 * Date: 2026/8/18
 * Description: 好友操作（拉黑/解除/删除/查询）请求参数
 */
@Data
public class FriendActionDTO {

    /**
     * 对方用户编码
     */
    private String friendCode;
}
