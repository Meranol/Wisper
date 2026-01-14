package com.example.wisper_one.websocket.chat_group.mapper;

import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * File: ChatGroupMessageMapper
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */
@Mapper
public interface ChatGroupMessageMapper {

    /**
     * 插入群消息
     */
    int insert(ChatGroupMessageEntity message);

    /**
     * 查询某个用户在群里的未读消息
     */
    List<ChatGroupMessageEntity> selectUnreadMessages(@Param("groupId") Long groupId,
                                                      @Param("userId") String userId);

    /**
     * 更新消息为已读
     */
    int updateMessageReadState(@Param("messageId") Long messageId, @Param("userId") String userId);

    /**
     * 为用户生成未读消息记录
     */
    int insertMessageReadRecord(@Param("messageId") Long messageId, @Param("userId") String userId);
}