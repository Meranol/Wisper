package com.example.wisper_one.websocket.chat.friend.service;

import com.example.wisper_one.websocket.chat.friend.POJO.FriendRelationEntity;

/**
 * File: FriendService
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description:好友关系service
 */
public interface FriendRelationService {

    void blockFriend(String friendCode);

    void unblockFriend(String friendCode);

    void deleteFriend(String friendCode);

    FriendRelationEntity getRelation(String friendCode);
}
