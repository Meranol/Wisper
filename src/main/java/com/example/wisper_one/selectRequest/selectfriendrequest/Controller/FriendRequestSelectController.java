package com.example.wisper_one.selectRequest.selectfriendrequest.Controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.finduserlist.POJO.SelectUserFriendListDTO;
import com.example.wisper_one.selectRequest.selectfriendrequest.service.FriendRequestSelectService;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendRequestEntity;
import com.example.wisper_one.websocket.chat.friend.service.FriendRequestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * File: FriendRequestController
 * Author: [周玉诚]
 * Date: 2026/2/24
 * Description:查询好友申请接口
 */
@RestController
@RequestMapping("/friend/selectlist")
public class FriendRequestSelectController {
    @Resource
    private FriendRequestSelectService selectService;

    @PostMapping("/list")
    public Result<?> list() {
        List<FriendRequestEntity> list = selectService.selectFriendRequest();
        return  Result.success(list);
    }

}
