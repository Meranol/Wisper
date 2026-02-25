package com.example.wisper_one.userUpdata.changeuseravatarurl.controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.userUpdata.changeuseravatarurl.DTO.ChangeUserAvatarurlDto;
import com.example.wisper_one.userUpdata.changeuseravatarurl.service.ChangeUserAvatarurlSerivce;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * File: ChangeUserAvatarurlController
 * Author: [周玉诚]
 * Date: 2026/2/24
 * Description:
 */
@RestController
@RequestMapping("/changeuseravatarurl")
public class ChangeUserAvatarurlController {

    @Resource
    private ChangeUserAvatarurlSerivce service;
    @PostMapping("/changeavatarurl")
    public Result<ChangeUserAvatarurlDto> changeUserAvatarurl(@RequestBody ChangeUserAvatarurlDto changeUserAvatarurlDto) {


        ChangeUserAvatarurlDto dto = service.changeUserAvatarurlDto(changeUserAvatarurlDto);
        return Result.success(dto);
    }



}
