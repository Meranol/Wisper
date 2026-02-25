package com.example.wisper_one.selectRequest.selectgrouprequest.Controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.selectRequest.selectgrouprequest.DTO.SelectGroupRequestDTO;
import com.example.wisper_one.selectRequest.selectgrouprequest.service.GroupRequestSelectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * File: GroupRequestSelectController
 * Author: [周玉诚]
 * Date: 2026/2/25
 * Description:
 */
@RestController
@RequestMapping("/GroupRequestSelect")
public class GroupRequestSelectController {
    @Resource
    private GroupRequestSelectService groupRequestSelectService;
    @PostMapping("/list")
    public Result<?> list () {
        List<SelectGroupRequestDTO> list = groupRequestSelectService.groupRequestSelect();
        return Result.success(list);
    }
}
