package com.example.wisper_one.websocket.chat.mapper;

import com.example.wisper_one.websocket.chat.POJO.ChatMessageEntity;
import io.lettuce.core.dynamic.annotation.Param;

import java.time.LocalDateTime;

/**
 * File: ChatMessageMapper
 * Author: [周玉诚]
 * Date: 2026/1/12
 * Description:
 */
public interface ChatMessageMapper {
    int insert(ChatMessageEntity entity);
    ChatMessageEntity selectLatestByReceiver(@Param("receiver") String receiver);

    int revoke (@Param("id") Long id,@Param("revokedAt") LocalDateTime revokedAt);
}
