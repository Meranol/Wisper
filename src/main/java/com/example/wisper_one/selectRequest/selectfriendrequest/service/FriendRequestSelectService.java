package com.example.wisper_one.selectRequest.selectfriendrequest.service;

import com.example.wisper_one.finduserlist.POJO.SelectUserFriendListDTO;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendRequestEntity;

import java.util.List;

/**
 * File: FriendRequestSelectService
 * Author: [周玉诚]
 * Date: 2026/2/24
 * Description:
 */
public interface FriendRequestSelectService {

    List<FriendRequestEntity> selectFriendRequest();


}
