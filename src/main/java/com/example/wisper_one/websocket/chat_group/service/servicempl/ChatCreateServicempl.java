package com.example.wisper_one.websocket.chat_group.service.servicempl;

import com.example.wisper_one.Login.POJO.UserPo;
import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat.mapper.ChatMessageMapper;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupEntity;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupCreateMapper;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMemberMapper;
import com.example.wisper_one.websocket.chat_group.service.ChatCreateService;
import com.example.wisper_one.websocket.chat_group.util.GroupCodeUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.Authenticator;

/**
 * File: ChatCreateServicempl
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */
@Service
public class ChatCreateServicempl implements ChatCreateService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ChatGroupCreateMapper chatGroupCreateMapper;

    @Resource
    private ChatGroupMemberMapper chatGroupMemberMapper;

    @Override
    public ChatGroupEntity createChatGroup(ChatGroupEntity chatGroup) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserPo userPo = userMapper.selectUserByUsername(name);
        if (userPo == null) {
            throw new BusinessException("当前用户不存在");
        }

        String code = GroupCodeUtil.generateGroupCode();

        ChatGroupEntity chatGroupEntity = new ChatGroupEntity();
        chatGroupEntity.setName(chatGroup.getName());
        chatGroupEntity.setGroupCode(code);
        chatGroupEntity.setOwner(userPo.getPublicId());

        int row = chatGroupCreateMapper.insert(chatGroupEntity);
        if (row != 1) {
            throw new BusinessException("群聊创建失败");
        }

        ChatGroupMemberEntity chatGroupMemberEntity = new ChatGroupMemberEntity();
        chatGroupMemberEntity.setGroupCode(code);
        chatGroupMemberEntity.setUserCode(userPo.getPublicId());
        chatGroupMemberEntity.setMemberNick(name);
        chatGroupMemberEntity.setRole(2);
        chatGroupMemberEntity.setJoinTime(java.time.LocalDateTime.now());

        int memberRow = chatGroupMemberMapper.insert(chatGroupMemberEntity);
        if (memberRow != 1) {
            throw new BusinessException("群主加入群聊失败");
        }

        return chatGroupEntity;
    }
}
