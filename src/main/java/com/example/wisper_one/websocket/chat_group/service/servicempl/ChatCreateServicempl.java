package com.example.wisper_one.websocket.chat_group.service.servicempl;

import com.example.wisper_one.Login.POJO.UserPo;
import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat.mapper.ChatMessageMapper;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupEntity;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupCreateMapper;
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
    private  UserMapper userMapper;
    @Resource
    private ChatGroupCreateMapper chatGroupCreateMapper;

    @Override
    public ChatGroupEntity createChatGroup(ChatGroupEntity chatGroup) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserPo userPo = userMapper.selectUserByUsername(name);
        System.out.println(userPo);
        if (userPo == null) {
            throw new BusinessException("当前用户不存在");
        }


        ChatGroupEntity chatGroupEntity = new ChatGroupEntity();
        chatGroupEntity.setName(chatGroup.getName());
        chatGroupEntity.setGroupCode(GroupCodeUtil.generateGroupCode());
        chatGroupEntity.setOwner(userPo.getPublicId());

        int row = chatGroupCreateMapper.insert(chatGroupEntity);

        if (row!=1){
            throw new BusinessException("群聊创建失败");
        }

        return chatGroupEntity;
    }
}
