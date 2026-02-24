package com.example.wisper_one.websocket.showchatmessage.chatmessage.Service.Servicempl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMapper;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMemberMapper;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.Mapper.GroupChatMessageSelectMapper;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.GroupChatMessageSelectDto;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.GroupChatMessageSelectVo;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.Service.GroupChatMessageSelectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * File: GroupChatMessageSelectmpl
 * Author: [周玉诚]
 * Date: 2026/2/14
 * Description:
 */
@Service
public class GroupChatMessageSelectmpl implements GroupChatMessageSelectService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private ChatGroupMapper chatGroupMapper;
    @Resource
    private GroupChatMessageSelectMapper groupChatMessageSelectMapper;
    @Override
    public List<GroupChatMessageSelectVo> getGroupMessages(GroupChatMessageSelectDto dto) {
        String username = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String usercode = userMapper.selectCodeByUname(username);

        ChatGroupMemberEntity result = chatGroupMapper.selectMember(dto.getGroupCode(), usercode);
        if (result == null) {
            throw new BusinessException("聊天记录查询失败，你不在此群");
        }

        if (dto.getPageSize() == null || dto.getPageSize() <= 0) {
            dto.setPageSize(20);
        }

        if (dto.getPageSize() > 100) {
            dto.setPageSize(100);
        }

        dto.setUserCode(usercode);

        return groupChatMessageSelectMapper.selectGroupMessages(dto);
    }
}
