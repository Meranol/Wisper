package com.example.wisper_one.finduserlist.POJO;

import lombok.Data;

/**
 * File: SelectUserFriendListDTO
 * Author: [周玉诚]
 * Date: 2026/2/10
 * Description:
 */
@Data


public class SelectUserFriendListDTO {
    private String friendCode;      // 好友公用ID
    private String nickname;        // 好友昵称
    private String avatar;          // 头像URL
    private String latestMsg;       // 最新消息内容
    private String latestMsgType;   // 消息类型：text/image
    private String latestMsgTime;   // 最新消息时间
    private int unreadCount;        // 未读消息数
}