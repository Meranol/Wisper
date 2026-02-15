package com.example.wisper_one.websocket.showchatmessage.chatmessage.Mapper;

import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.GroupChatMessageSelectDto;
import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.GroupChatMessageSelectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * File: GroupChatMessageSelectMapper
 * Author: [周玉诚]
 * Date: 2026/2/13
 * Description:
 */
@Mapper
public interface GroupChatMessageSelectMapper {

    List<GroupChatMessageSelectVo> selectGroupMessages(@Param("dto") GroupChatMessageSelectDto dto);

}
