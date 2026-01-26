package com.example.wisper_one.websocket.chat_group.applyjoin.service.servicelmpl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat_group.applyjoin.POJO.MemberRequestEntity;
import com.example.wisper_one.websocket.chat_group.applyjoin.mapper.MemberRequestMapper;
import com.example.wisper_one.websocket.chat_group.applyjoin.service.MemberRequestService;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupCreateMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * File: MemberProcessServicempl
 * Author: [周玉诚]
 * Date: 2026/1/24
 * Description:
 */
@Service
public class MemberRequestServicempl implements MemberRequestService {

    @Resource
    UserMapper userMapper;
    @Resource
    MemberRequestMapper memberRequestMapper;
    @Resource
    ChatGroupCreateMapper chatGroupCreateMapper;


    @Transactional//回滚机制
    @Override
    public MemberRequestEntity join(MemberRequestEntity memberRequestPoJo) {

        String username = (String) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        String fromUserCode = userMapper.selectCodeByUname(username);
        if (fromUserCode == null) {
            throw new BusinessException("token解析用户名失败");
        }

        if (memberRequestPoJo.getToGroup() == null
                || memberRequestPoJo.getToGroup().isEmpty()) {
            throw new BusinessException("请输入加入目标群聊");
        }

        if (chatGroupCreateMapper.selectgroupbygroupcode(memberRequestPoJo.getToGroup()) == null) {
            throw new BusinessException("不存在的群聊");
        }

        if(memberRequestMapper.selectifin(memberRequestPoJo.getToGroup(), fromUserCode) != null) {
            throw new BusinessException("你以在群聊无需申请");
        }
        Object o = memberRequestMapper.selectMemberRequest( fromUserCode,memberRequestPoJo.getToGroup());
        System.out.println(o);
        if (o!=null) {
            throw new BusinessException("你以提交过申请");
        }


        memberRequestPoJo.setFromUserCode(fromUserCode);
        memberRequestPoJo.setStatus(0);
        memberRequestPoJo.setCreateTime(LocalDateTime.now());

        int row = memberRequestMapper.createMemberRequest(memberRequestPoJo);
        if (row !=1) {
            throw new BusinessException("群聊加入申请发送失败");
        }


        return memberRequestPoJo;
    }

}
