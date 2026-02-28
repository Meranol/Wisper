package com.example.wisper_one.RedPacket.controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.RedPacket.POJO.DTO.GrabRedPacketRequestDTO;
import com.example.wisper_one.RedPacket.POJO.DTO.GrabRedPacketResponseDTO;
import com.example.wisper_one.RedPacket.POJO.DTO.SendRedPacketRequestDTO;
import com.example.wisper_one.RedPacket.POJO.DTO.SendRedPacketResponseDTO;
import com.example.wisper_one.RedPacket.service.RedPacketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * File: RedPacketController
 * Author: [周玉诚]
 * Date: 2026/2/26
 * Description:抢红包接口
 */
@RestController
@RequestMapping("/redpacket")
public class RedPacketController {


    @Resource
    private RedPacketService redPacketService;

    @PostMapping("/grab")
    public Result<?> grab(@RequestBody GrabRedPacketRequestDTO requestDTO) {
        return Result.success(redPacketService.grab(requestDTO));
    }

    @PostMapping("/send")
    public  Result<?>  send(@RequestBody SendRedPacketRequestDTO requestDTO) {
        return Result.success(redPacketService.send(requestDTO));
    }


}
