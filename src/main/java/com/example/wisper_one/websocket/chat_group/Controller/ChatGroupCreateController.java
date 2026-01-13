package com.example.wisper_one.websocket.chat_group.Controller;

import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupEntity;
import com.example.wisper_one.websocket.chat_group.service.ChatCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * File: ChatGroupCreateController
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */
@RestController
@RequestMapping("/chatgroup")
public class ChatGroupCreateController {
//
//    private final ChatCreateService chatCreateService;
//
//    public ChatGroupCreateController(ChatCreateService chatCreateService) {
//        this.chatCreateService = chatCreateService;
//    }   <-------此为构造注入@Resource自动生成


    @Resource
    private  ChatCreateService chatCreateService;

    @PostMapping("/creategroup")
    public ResponseEntity<ChatGroupEntity> createGroup(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        if (name == null || name.isEmpty()) {
            throw new BusinessException("群名称不能为空");
        }

        ChatGroupEntity chatGroup = new ChatGroupEntity();
        chatGroup.setName(name);

        ChatGroupEntity createdGroup = chatCreateService.createChatGroup(chatGroup);

        return ResponseEntity.ok(createdGroup);
    }
}
