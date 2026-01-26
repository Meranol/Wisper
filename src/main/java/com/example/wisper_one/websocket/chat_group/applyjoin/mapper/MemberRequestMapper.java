package com.example.wisper_one.websocket.chat_group.applyjoin.mapper;

import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import com.example.wisper_one.websocket.chat_group.applyjoin.POJO.MemberRequestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * File: MemberProcessMapper
 * Author: [周玉诚]
 * Date: 2026/1/24
 * Description:
 */
@Mapper
public interface MemberRequestMapper {

    int createMemberRequest (MemberRequestEntity memberRequestPoJo);

    ChatGroupMemberEntity selectifin (@Param("group_code") String group_code,@Param("user_code") String user_code);

    MemberRequestEntity selectMemberRequest (@Param("from_user_code") String from_user_code, @Param("to_group") String to_group);
}
