package com.example.wisper_one.websocket.chat_group.applyjoin.service.servicelmpl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import com.example.wisper_one.websocket.chat_group.applyjoin.POJO.MemberRequestHandlerDTO;
import com.example.wisper_one.websocket.chat_group.applyjoin.mapper.MemberRequestHandlerMapper;
import com.example.wisper_one.websocket.chat_group.applyjoin.service.MemberRequestHandlerService;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMapper;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMemberMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.time.LocalDateTime;

/**
 * File: MemberRequestHandlerServicempl
 * Author: [周玉诚]
 * Date: 2026/1/25
 * Description:  群聊申请处理
 */
@Service
public class MemberRequestHandlerServicempl implements MemberRequestHandlerService {


    @Resource
    private UserMapper userMapper;
    @Resource
    private MemberRequestHandlerMapper memberRequestHandlerMapper;
    @Resource
    private ChatGroupMemberMapper chatGroupMemberMapper;
    @Resource
    ChatGroupMapper chatGroupMapper;

    @Transactional
    @Override
    public MemberRequestHandlerDTO handle(MemberRequestHandlerDTO dto) {

        String username = (String) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        String handlerCode = userMapper.selectCodeByUname(username);
        System.out.println(dto.getId()+"处理群号id💩");
        String group = memberRequestHandlerMapper.selectGroupCodeById(dto.getId());
        System.out.println(group+"处理群号💩");
        Integer role = chatGroupMapper.selectRoleByUcode(handlerCode,group);
        System.out.println(role+"💩");

        if (role ==0) {
            throw new BusinessException("你无权限");
        }



        dto.setHandlername(handlerCode);
        dto.setHandleTime(LocalDateTime.now());

        Integer oldStatus = memberRequestHandlerMapper.selectStatusById(dto.getId());
        if (oldStatus == null || oldStatus != 0) {
            throw new BusinessException("该申请已被处理");
        }

        int rows = memberRequestHandlerMapper.updateById(dto);
        if (rows != 1) {
            throw new BusinessException("处理失败");
        }
        Integer checkstatus = memberRequestHandlerMapper.selectStatusById(dto.getId());
        String ucode = memberRequestHandlerMapper.selectucodeById(dto.getId());
        String name = userMapper.selectUnameByCode(ucode);
        if (checkstatus ==1) {
            ChatGroupMemberEntity chatGroupMemberEntity = new ChatGroupMemberEntity();
            chatGroupMemberEntity.setJoinTime(LocalDateTime.now());
            chatGroupMemberEntity.setUserCode(ucode);
            chatGroupMemberEntity.setRole(0);
            chatGroupMemberEntity.setGroupCode(group);
            chatGroupMemberEntity.setMemberNick(name);
            int row = chatGroupMemberMapper.insert(chatGroupMemberEntity);

            if (row != 1) {
                throw new BusinessException("群成员插入失败");
            }
        }

        return dto;
    }





}
