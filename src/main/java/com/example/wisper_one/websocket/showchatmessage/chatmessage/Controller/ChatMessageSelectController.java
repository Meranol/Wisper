package com.example.wisper_one.websocket.showchatmessage.chatmessage.Controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.ChatMessageSelectDto;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.Service.ChatMessageSelectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * File: ChatMessageSelectController
 * Author: [周玉诚]
 * Date: 2026/2/13
 * Description:
 */
@RestController
@RequestMapping("/selectchatmessage")
public class ChatMessageSelectController {

    @Resource
    private ChatMessageSelectService chatMessageSelectService;

    @PostMapping("/chatmessagelist")
    public Result<?> list (@RequestBody ChatMessageSelectDto dto) {


        List<ChatMessageSelectDto> list = chatMessageSelectService.getChatMessageSelectService(dto);


        return Result.success(list);
    }


}
