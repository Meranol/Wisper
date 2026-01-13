package com.example.wisper_one.websocket.chat_group.POJO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: ChatGroupEntity
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description: 群表对应实体类
 */
@Data
public class ChatGroupEntity {

    /** 群ID，数据库自增主键 */
    private Long id;

    /** 创建时间，默认当前时间 */
    private LocalDateTime createTime = LocalDateTime.now();

    /** 群名称 */
    private String name;

    /** 群主用户ID */
    private String owner;

    /**群号 */
    private String groupCode;

    /** 群状态1=正常，0=解散封禁 */
    private Integer status = 1;

    /** 是否可直接加入0=不允许1=允许 */
    private Integer joinAble = 0;
}
