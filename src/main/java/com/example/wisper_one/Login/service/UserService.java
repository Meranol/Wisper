package com.example.wisper_one.Login.service;

import com.example.wisper_one.Login.DTO.request.CheckUnameDto;
import com.example.wisper_one.Login.DTO.request.LoginRequestDto;
import com.example.wisper_one.Login.DTO.request.RegRequestDto;
import com.example.wisper_one.Login.POJO.UserPo;
/**
 * File: UserService
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
public interface UserService  {
    UserPo register(RegRequestDto regRequest);

    Boolean checkUsername(CheckUnameDto checkUname);

    UserPo login(LoginRequestDto loginRequest);
}
