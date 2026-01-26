package com.example.wisper_one.websocket.chat.friend.service.servicelmpl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.utils.jwt.JwtTokenUtil;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendRequestEntity;
import com.example.wisper_one.websocket.chat.friend.mapper.FriendRequestMapper;
import com.example.wisper_one.websocket.chat.friend.service.FriendRequestService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * File: FriendRequestServicempl
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description:好友申请servicempl
 */
@Service
public class FriendRequestServicempl implements FriendRequestService {


    @Resource
    FriendRequestMapper friendRequestMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public FriendRequestEntity createFriendRequest(FriendRequestEntity friendRequest) {


        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String fromUserCode = userMapper.selectCodeByUname(username);
        friendRequest.setFromUserCode(fromUserCode);

        if (friendRequest == null) {
            throw new BusinessException("好友申请参数为空");
        }

        if (friendRequest.getFromUserCode() == null
                || friendRequest.getFromUserCode().isEmpty()) {
            throw new BusinessException("申请人不能为空");
        }
        if (friendRequest.getToUserCode() == null
                || friendRequest.getToUserCode().isEmpty()) {
            throw new BusinessException("被申请人不能为空");
        }

        if (friendRequest.getFromUserCode()
                .equals(friendRequest.getToUserCode())) {
            throw new BusinessException("不能添加自己为好友");
        }
        String result = userMapper.selectUnameByCode(friendRequest.getToUserCode());
        if (result == null) {
            throw new BusinessException("查无此人");
        }

        friendRequest.setStatus(0);
        friendRequest.setCreateTime(LocalDateTime.now());

        System.out.println("好友重复申请✅️"+friendRequest.getFromUserCode()+friendRequest.getToUserCode());
        FriendRequestEntity existingRequest = friendRequestMapper.selectExistingRequest(friendRequest.getFromUserCode(), friendRequest.getToUserCode());
        if (existingRequest != null ) {
            throw new BusinessException("已经存在未处理的好友申请");
        }
        FriendRequestEntity Request = friendRequestMapper.selectRequest(friendRequest.getFromUserCode(), friendRequest.getToUserCode());
        if (Request != null ) {
            throw new BusinessException("好友申请已通过");
        }

        int row = friendRequestMapper.insertFriendRequest(friendRequest);
        if (row != 1) {
            throw new BusinessException("好友申请发送失败");
        }

        return friendRequest;
    }
}
