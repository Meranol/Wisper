package com.example.wisper_one.administrator.kick.controller;

import com.example.wisper_one.Login.DTO.request.LoginRequestDto;
import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.administrator.kick.DTO.KickDTO;
import com.example.wisper_one.administrator.kick.service.Kickservice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * File: KickController
 * Author: [周玉诚]
 * Date: 2026/2/3
 * Description: 踢人
 */

@RestController
@RequestMapping("/admin")
public class KickController {
    @Resource
    private Kickservice kickService;


    @PostMapping("/kick")
    public Result<Map<String,Object>> kick(@RequestBody KickDTO kickDTO){

        boolean success = kickService.kick(kickDTO);

        Map<String,Object> data = Map.of(
                "public_id", kickDTO.getPublic_id(),
                "success", success
        );

        if(success){
            return Result.success("用户已被踢出", data);
        } else {
            return Result.failure("用户未在线或已下线");
        }
    }

}
