package com.example.wisper_one.selectRequest.selectgrouprequest.service;

import com.example.wisper_one.selectRequest.selectgrouprequest.DTO.SelectGroupRequestDTO;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendRequestEntity;

import java.util.List;

/**
 * File: GroupRequestSelectService
 * Author: [周玉诚]
 * Date: 2026/2/24
 * Description:
 */
public interface GroupRequestSelectService {

     List<SelectGroupRequestDTO> groupRequestSelect();

}
