package com.example.wisper_one.websocket.chat_group.mapper;

import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * File: ChatGroupCreateMapper
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */
@Mapper
public interface ChatGroupCreateMapper {

    int insert(ChatGroupEntity chatGroupEntity);


    ChatGroupEntity selectgroupbygroupcode(@Param("group_code") String group_code);


}
