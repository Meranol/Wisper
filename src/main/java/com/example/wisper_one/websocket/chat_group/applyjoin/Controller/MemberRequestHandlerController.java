package com.example.wisper_one.websocket.chat_group.applyjoin.Controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.websocket.chat_group.applyjoin.POJO.MemberRequestHandlerDTO;
import com.example.wisper_one.websocket.chat_group.applyjoin.service.MemberRequestHandlerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * File: MemberRequestHandlerController
 * Author: [周玉诚]
 * Date: 2026/1/25
 * Description:管理接受群聊申请
 */
@RestController
@RequestMapping("/groupmemberhandler")
public class MemberRequestHandlerController {

    @Resource
    private MemberRequestHandlerService memberRequestHandlerService;


    @PostMapping("/Request")
    public Result<?> MemberRequestHandler (@RequestBody MemberRequestHandlerDTO memberRequestHandlerEntity) {

        memberRequestHandlerService.handle(memberRequestHandlerEntity);
        return Result.success(memberRequestHandlerEntity);
    }
}
