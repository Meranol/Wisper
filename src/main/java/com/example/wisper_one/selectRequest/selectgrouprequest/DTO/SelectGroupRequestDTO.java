package com.example.wisper_one.selectRequest.selectgrouprequest.DTO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * File: SelectGroupRequestDTO
 * Author: [周玉诚]
 * Date: 2026/2/25
 * Description:
 */
@Data
public class SelectGroupRequestDTO {
    private Long id;




    /**
     * 申请人
     */
    private String fromUserCode;

    /**
     * 目标群聊
     */
    private String toGroup;
    /**
     * 申请状态
     * 0：待处理
     * 1：同意
     * 2：拒绝
     */
    private Integer status;

    /**
     * 申请时间
     */
    private LocalDateTime createTime;
    /**
     * 申请理由
     */
    private String remark;
    /**
     * 处理人
     */
    private String handlername;
    /**
     * 处理时间（同意/拒绝）
     */
    private LocalDateTime handleTime;
}
