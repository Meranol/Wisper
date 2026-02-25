package com.example.wisper_one.Login.service;

import com.example.wisper_one.Login.DTO.CheckUnameDto;
import com.example.wisper_one.Login.DTO.LoginRequestDto;
import com.example.wisper_one.Login.DTO.RegRequestDto;
import com.example.wisper_one.Login.DTO.SelectuserDTO;
import com.example.wisper_one.Login.POJO.UserPo;
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

