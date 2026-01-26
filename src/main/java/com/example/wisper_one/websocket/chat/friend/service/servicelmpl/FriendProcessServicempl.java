package com.example.wisper_one.websocket.chat.friend.service.servicelmpl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendProcessDTO;

import com.example.wisper_one.websocket.chat.friend.POJO.FriendRelationEntity;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendRequestEntity;
import com.example.wisper_one.websocket.chat.friend.mapper.FriendRelationMapper;
import com.example.wisper_one.websocket.chat.friend.mapper.FriendRequestMapper;
import com.example.wisper_one.websocket.chat.friend.service.FriendProcessService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * File: FriendProcessServicempl
 * Author: [周玉诚]
 * Date: 2026/1/23
 * Description: 接受好友申请
 */
@Service
public class FriendProcessServicempl implements FriendProcessService {

    @Resource
    private FriendRequestMapper friendRequestMapper;
    @Resource
    private FriendRelationMapper friendRelationMapper;

    @Resource
    private UserMapper userMapper;

    @Transactional//回滚机制
    @Override
    public FriendProcessDTO process(FriendProcessDTO dto) {

        String username = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String currentUserCode = userMapper.selectCodeByUname(username);

        if (dto.getFromUserCode() == null || dto.getStatus() == null) {
            throw new BusinessException("参数错误");
        }

        if (dto.getStatus() != 1 && dto.getStatus() != 2) {
            throw new BusinessException("非法操作");
        }

        FriendRequestEntity request =
                friendRequestMapper.selectExistingRequest(dto.getFromUserCode(),currentUserCode);

        if (request == null) {
            throw new BusinessException("好友申请不存在");
        }

        if (!currentUserCode.equals(request.getToUserCode())) {
            throw new BusinessException("无权处理该好友申请");
        }

        if (request.getStatus() != 0) {
            throw new BusinessException("好友申请已处理");
        }

        request.setStatus(dto.getStatus() == 1 ? 1 : 2);

        request.setHandleTime(LocalDateTime.now());
        friendRequestMapper.updateStatus(request);


        FriendRelationEntity friendRelationEntity = new FriendRelationEntity();
        //建立好友
        if (dto.getStatus() == 1) {
            friendRelationEntity.setUserCode(currentUserCode);
            friendRelationEntity.setFriendCode(dto.getFromUserCode());

            friendRelationEntity.setStatus(1);
            friendRelationEntity.setCreateTime(LocalDateTime.now());


            friendRelationMapper.insertFriendRequest(
                    friendRelationEntity
            );


        }
        return dto;
    }
}
