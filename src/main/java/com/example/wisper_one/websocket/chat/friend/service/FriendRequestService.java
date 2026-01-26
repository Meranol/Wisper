package com.example.wisper_one.websocket.chat.friend.service;

import com.example.wisper_one.websocket.chat.friend.POJO.FriendRequestEntity;

/**
 * File: FriendRequestService
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description:好友申请service
 */
public interface FriendRequestService {

    FriendRequestEntity createFriendRequest(FriendRequestEntity friendRequestEntity);

}
