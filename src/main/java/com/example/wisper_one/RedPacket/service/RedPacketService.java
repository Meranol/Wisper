package com.example.wisper_one.RedPacket.service;

import com.example.wisper_one.RedPacket.POJO.DTO.GrabRedPacketRequestDTO;
import com.example.wisper_one.RedPacket.POJO.DTO.GrabRedPacketResponseDTO;
import com.example.wisper_one.RedPacket.POJO.DTO.SendRedPacketRequestDTO;
import com.example.wisper_one.RedPacket.POJO.DTO.SendRedPacketResponseDTO;

/**
 * File: RedPacketService
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description: 
 */public interface RedPacketService {
    GrabRedPacketResponseDTO grab(GrabRedPacketRequestDTO requestDTO);
    SendRedPacketResponseDTO send(SendRedPacketRequestDTO requestDTO);

}
