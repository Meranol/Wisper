package com.example.wisper_one.finduserlist.Controller;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.finduserlist.POJO.SelectUserFriendListDTO;
import com.example.wisper_one.finduserlist.mapper.SelectUserFriendListMapper;
import com.example.wisper_one.finduserlist.service.SelectUserFriendListservice;
import com.example.wisper_one.finduserlist.service.servicempl.SelectUserFriendListservicempl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * File: SelectUserFriendListController
 * Author: [周玉诚]
 * Date: 2026/2/10
 * Description:
 */
@RestController
@RequestMapping("/selectfriendlist")
public class SelectUserFriendListController {

    @Resource
    private SelectUserFriendListservice selectUserFriendListservice;

    @PostMapping("/selectfriend")
    public Result<?> selectUserFriendList() {

        List<SelectUserFriendListDTO> list =
                selectUserFriendListservice.selectUserFriendList();

        return Result.success(list);
    }
}
