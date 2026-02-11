package com.example.wisper_one.finduserlist.service;

/**
 * File: SelectUserFriendListservice
 * Author: [周玉诚]
 * Date: 2026/2/10
 * Description:
 */

import com.example.wisper_one.finduserlist.POJO.SelectUserFriendListDTO;

import java.util.List;

public interface SelectUserFriendListservice {
    List<SelectUserFriendListDTO> selectUserFriendList();
}
