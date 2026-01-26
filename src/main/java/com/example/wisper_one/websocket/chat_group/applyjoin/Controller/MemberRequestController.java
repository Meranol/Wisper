package com.example.wisper_one.websocket.chat_group.applyjoin.Controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.websocket.chat_group.applyjoin.POJO.MemberRequestEntity;
import com.example.wisper_one.websocket.chat_group.applyjoin.service.MemberRequestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * File: MemberProcessController
 * Author: [周玉诚]
 * Date: 2026/1/24
 * Description:申请加入群`
 */
@RestController
@RequestMapping("/groupapply")
public class MemberRequestController {

    @Resource
    private MemberRequestService memberRequestService;


    @PostMapping("/Requestgroup")
    public Result<?> sendrequest(@RequestBody MemberRequestEntity memberRequestEntity) {
            memberRequestService.join(memberRequestEntity);
        return Result.success(memberRequestEntity);
    }
}
