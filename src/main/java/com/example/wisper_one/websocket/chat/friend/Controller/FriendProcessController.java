package com.example.wisper_one.websocket.chat.friend.Controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendProcessDTO;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendProcessDTO;
import com.example.wisper_one.websocket.chat.friend.service.FriendProcessService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * File: FriendProcessController
 * Author: [周玉诚]
 * Date: 2026/1/23
 * Description:
 */
@RestController
@RequestMapping("/friend/request")
public class FriendProcessController {

    @Resource
    private FriendProcessService friendProcessService;

    @PostMapping("/process")
    public Result<Void> process(@RequestBody FriendProcessDTO dto) {
        friendProcessService.process(dto);
        return Result.success();
    }
}
