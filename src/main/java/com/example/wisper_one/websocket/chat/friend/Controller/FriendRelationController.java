package com.example.wisper_one.websocket.chat.friend.Controller;

import com.example.wisper_one.usercontroller.common.Result;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendActionDTO;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendRelationEntity;
import com.example.wisper_one.websocket.chat.friend.service.FriendRelationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * File: FriendRelationController
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description:   好友关系接口
 */
@RestController
@RequestMapping("/friend")
public class FriendRelationController {

    @Resource
    private FriendRelationService friendRelationService;

    @PostMapping("/block")
    public Result<Void> blockFriend(@RequestBody FriendActionDTO dto) {
        friendRelationService.blockFriend(dto.getFriendCode());
        return Result.success();
    }

    @PostMapping("/unblock")
    public Result<Void> unblockFriend(@RequestBody FriendActionDTO dto) {
        friendRelationService.unblockFriend(dto.getFriendCode());
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<Void> deleteFriend(@RequestBody FriendActionDTO dto) {
        friendRelationService.deleteFriend(dto.getFriendCode());
        return Result.success();
    }

    @PostMapping("/relation")
    public Result<FriendRelationEntity> getRelation(@RequestBody FriendActionDTO dto) {
        return Result.success(friendRelationService.getRelation(dto.getFriendCode()));
    }
}
