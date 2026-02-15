package com.example.wisper_one.websocket.showchatmessage.chatmessage.Mapper;

import com.example.wisper_one.websocket.showchatmessage.chatmessage.POJO.ChatMessageSelectDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * File: ChatMessageSelectMapper
 * Author: [周玉诚]
 * Date: 2026/2/13
 * Description:聊天记录搜索
 */
@Mapper
public interface ChatMessageSelectMapper {

    List<ChatMessageSelectDto> selectChatHistory(
            @Param("currentUser") String currentUser,
            @Param("friendCode") String friendCode,
            @Param("lastId") Long lastId,
            @Param("pageSize") Integer pageSize,
            @Param("type") String type
    );


}
