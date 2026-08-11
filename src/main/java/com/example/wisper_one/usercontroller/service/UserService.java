package com.example.wisper_one.usercontroller.service;

import com.example.wisper_one.usercontroller.DTO.CheckUnameDto;
import com.example.wisper_one.usercontroller.DTO.LoginRequestDto;
import com.example.wisper_one.usercontroller.DTO.RegRequestDto;
import com.example.wisper_one.usercontroller.DTO.SelectuserDTO;
import com.example.wisper_one.usercontroller.POJO.UserPo;
/**
 * File: UserService
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
public interface UserService {
    UserPo register(RegRequestDto regRequest);

    Boolean checkUsername(CheckUnameDto checkUname);

    UserPo login(LoginRequestDto loginRequest);

    SelectuserDTO getUserByPublicId(String publicId);


}

