package com.example.wisper_one.websocket.chat_group.service.servicempl;

import com.example.wisper_one.Login.POJO.UserPo;
import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMessageEntity;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMapper;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMemberMapper;
import com.example.wisper_one.websocket.chat_group.service.ChatGroupJoinService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * File: ChatGroupJoinService
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */

@Service
public class ChatGroupJoinServicempl implements ChatGroupJoinService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private ChatGroupMemberMapper chatGroupMemberMapper;
    @Override
    public ChatGroupMemberEntity join(ChatGroupMemberEntity chatGroupMapper) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserPo userPo = userMapper.selectUserByUsername(name);


        if (userPo == null) {
            throw new BusinessException("当前用户不存在");
        }

        ChatGroupMemberEntity chatGroupMemberEntity = new ChatGroupMemberEntity();
        chatGroupMemberEntity.setJoinTime(LocalDateTime.now());
        chatGroupMemberEntity.setUserCode(userPo.getPublicId());
        chatGroupMemberEntity.setRole(0);
        chatGroupMemberEntity.setGroupCode(chatGroupMapper.getGroupCode());
        chatGroupMemberEntity.setMemberNick(name);

        int row = chatGroupMemberMapper.insert(chatGroupMemberEntity);

        if (row != 1) {
            throw new BusinessException("群成员插入失败");
        }
        return chatGroupMemberEntity;
    }
}
