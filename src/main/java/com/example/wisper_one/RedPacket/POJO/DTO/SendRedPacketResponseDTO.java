package com.example.wisper_one.RedPacket.POJO.DTO;
import lombok.Data;


/**
 * File: SendRedPacketResponseDTO
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description:发红包响应 DTO
 */
@Data
public class SendRedPacketResponseDTO {
    private boolean success;
    private Long redPacketId;
    private String msg;
}