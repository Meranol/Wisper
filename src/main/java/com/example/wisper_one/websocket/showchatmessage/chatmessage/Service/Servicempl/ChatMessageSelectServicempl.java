package com.example.wisper_one.websocket.showchatmessage.chatmessage.Service.Servicempl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.Mapper.ChatMessageSelectMapper;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.ChatMessageSelectDto;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.Service.ChatMessageSelectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * File: ChatMessageSelectServicempl
 * Author: [周玉诚]
 * Date: 2026/2/13
 * Description:
 */
@Service
public class ChatMessageSelectServicempl implements ChatMessageSelectService {



    @Resource
    private UserMapper userMapper;
    @Resource
    private ChatMessageSelectMapper chatMessageSelectMapper;

    @Override
    public List<ChatMessageSelectDto> getChatMessageSelectService(ChatMessageSelectDto chatMessageSelectDto) {
        String username = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String usercode = userMapper.selectCodeByUname(username);




        return chatMessageSelectMapper.selectChatHistory(
                usercode,chatMessageSelectDto.getFriendCode(),
                chatMessageSelectDto.getLastId(),
                chatMessageSelectDto.getPageSize(),
                chatMessageSelectDto.getType()
                );
    }
}
