package com.example.wisper_one.websocket.showchatmessage.chatmessage.Controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.Mapper.GroupChatMessageSelectMapper;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.GroupChatMessageSelectDto;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.GroupChatMessageSelectVo;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.Service.GroupChatMessageSelectService;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * File: GroupChatMessageSelectController
 * Author: [周玉诚]
 * Date: 2026/2/13
 * Description:
 */


@RestController
@RequestMapping("/groupchatmessage")
public class GroupChatMessageSelectController {

    @Resource
    private GroupChatMessageSelectService groupChatMessageSelectService;
    @PostMapping("/list")  // 添加这个注解
    public Result<?> list(@RequestBody GroupChatMessageSelectDto dto){

        List<GroupChatMessageSelectVo> list = groupChatMessageSelectService.getGroupMessages(dto);

        return Result.success(list);
    }
}
