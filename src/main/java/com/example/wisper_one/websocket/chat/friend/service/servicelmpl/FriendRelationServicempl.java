package com.example.wisper_one.websocket.chat.friend.service.servicelmpl;

import com.example.wisper_one.usercontroller.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat.friend.POJO.FriendRelationEntity;
import com.example.wisper_one.websocket.chat.friend.mapper.FriendRelationMapper;
import com.example.wisper_one.websocket.chat.friend.service.FriendRelationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * File: FriendRelationServicempl
 * Author: [周玉诚]
 * Date: 2026/1/19
 * Description:好友关系servicempl
 */
@Service
public class FriendRelationServicempl implements FriendRelationService {

    @Resource
    private FriendRelationMapper friendRelationMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void blockFriend(String friendCode) {
        String currentUserCode = getCurrentUserCode();

        if (friendCode == null || friendCode.isEmpty()) {
            throw new BusinessException("好友参数不能为空");
        }

        // status: 1=正常 2=拉黑（禁用，无法聊天）；operator_code 记录是谁拉黑
        int row = friendRelationMapper.updateFriendStatus(currentUserCode, friendCode, 2, currentUserCode);

        if (row == 0) {
            throw new BusinessException("你们还不是好友");
        }
    }

    @Override
    public void unblockFriend(String friendCode) {
        String currentUserCode = getCurrentUserCode();

        if (friendCode == null || friendCode.isEmpty()) {
            throw new BusinessException("好友参数不能为空");
        }

        FriendRelationEntity relation = friendRelationMapper.selectRelation(currentUserCode, friendCode);
        if (relation == null) {
            throw new BusinessException("你们还不是好友");
        }
        if (relation.getStatus() == null || relation.getStatus() != 2) {
            throw new BusinessException("当前不是拉黑状态");
        }
        if (!currentUserCode.equals(relation.getOperatorCode())) {
            throw new BusinessException("只有拉黑方才能解除拉黑");
        }

        friendRelationMapper.updateFriendStatus(currentUserCode, friendCode, 1, null);
    }

    @Override
    public void deleteFriend(String friendCode) {
        String currentUserCode = getCurrentUserCode();

        if (friendCode == null || friendCode.isEmpty()) {
            throw new BusinessException("好友参数不能为空");
        }

        // status=3 表示已删除（双方解除，需重新申请）；operator_code 记录是谁删的（仅审计，无解除权）
        int row = friendRelationMapper.updateFriendStatus(currentUserCode, friendCode, 3, currentUserCode);

        if (row == 0) {
            throw new BusinessException("你们还不是好友");
        }
    }

    @Override
    public FriendRelationEntity getRelation(String friendCode) {
        String currentUserCode = getCurrentUserCode();
        if (friendCode == null || friendCode.isEmpty()) {
            throw new BusinessException("好友参数不能为空");
        }
        return friendRelationMapper.selectRelation(currentUserCode, friendCode);
    }

    private String getCurrentUserCode() {
        String username = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userMapper.selectCodeByUname(username);
    }
}
