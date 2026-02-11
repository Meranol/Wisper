package com.example.wisper_one.finduserlist.service.servicempl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.finduserlist.POJO.SelectUserFriendListDTO;
import com.example.wisper_one.finduserlist.mapper.SelectUserFriendListMapper;
import com.example.wisper_one.finduserlist.service.SelectUserFriendListservice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * File: SelectUserFriendListservicempl
 * Author: [周玉诚]
 * Date: 2026/2/10
 * Description:
 */

@Service
public class SelectUserFriendListservicempl implements SelectUserFriendListservice {

    @Resource
    private SelectUserFriendListMapper selectUserFriendListMapper;
    @Resource
    UserMapper userMapper;


    @Override
    public List<SelectUserFriendListDTO> selectUserFriendList() {
        String username = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String currentUserCode = userMapper.selectCodeByUname(username);

        return selectUserFriendListMapper.selectFriendListWithLastMsg(currentUserCode);
    }
}
