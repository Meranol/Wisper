package com.example.wisper_one.selectRequest.selectfriendrequest.service.servicempl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.selectRequest.selectfriendrequest.mapper.FriendRequestSelectMapper;
import com.example.wisper_one.selectRequest.selectfriendrequest.service.FriendRequestSelectService;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendRequestEntity;
import com.example.wisper_one.websocket.chat.friend.mapper.FriendRequestMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * File: FriendRequestSelectServicempl
 * Author: [周玉诚]
 * Date: 2026/2/24
 * Description:
 */
@Service
public class FriendRequestSelectServicempl implements FriendRequestSelectService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private FriendRequestSelectMapper friendRequestSelectMapper;

    @Override
    public List<FriendRequestEntity> selectFriendRequest() {
        String username = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String currentUserCode = userMapper.selectCodeByUname(username);



        return friendRequestSelectMapper.friendRequestEntityList(currentUserCode);

    }
}
