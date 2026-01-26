package com.example.wisper_one.websocket.chat.friend.Controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendRequestEntity;
import com.example.wisper_one.websocket.chat.friend.service.FriendRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * File: FriendRequestController
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description: 好友申请接口
 */

@RestController
@RequestMapping("/FriendRequest")
public class FriendRequestController {
    @Resource
    private FriendRequestService friendRequestService;

    @PostMapping("/send")
    public Result<FriendRequestEntity> send (@RequestBody FriendRequestEntity friendRequestEntity) {
        FriendRequestEntity result =
                friendRequestService.createFriendRequest(friendRequestEntity);

        return Result.success(result);
    }

}
