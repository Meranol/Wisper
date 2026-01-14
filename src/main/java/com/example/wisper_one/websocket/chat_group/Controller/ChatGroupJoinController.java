package com.example.wisper_one.websocket.chat_group.Controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupEntity;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMapper;
import com.example.wisper_one.websocket.chat_group.service.ChatGroupJoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * File: ChatGroupJoinController
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */
@RestController
@RequestMapping("join")
public class ChatGroupJoinController {


    @Resource
    private ChatGroupJoinService chatGroupJoinService;



    @Resource
    private ChatGroupMapper chatGroupMapper;
    @PostMapping("/joingroup")
    public Result<ChatGroupMemberEntity> join(@RequestBody Map<String, String> map) {
        String groupCode = map.get("groupCode");
        if (groupCode == null) {
            throw new BusinessException("群号不能为空");
        }


        ChatGroupEntity group = chatGroupMapper.selectByGroupCode(groupCode);
        if (group == null) {
            throw new BusinessException("群号不存在");
        }


        ChatGroupMemberEntity member = new ChatGroupMemberEntity();
        member.setGroupCode(groupCode);
        return Result.success(chatGroupJoinService.join(member));


    }

}
