package com.example.wisper_one.Login.service;

import com.example.wisper_one.Login.DTO.request.CheckUname;
import com.example.wisper_one.Login.DTO.request.LoginRequest;
import com.example.wisper_one.Login.DTO.request.RegRequest;
import com.example.wisper_one.Login.domain.User;
/**
 * File: UserService
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
public interface UserService {
    User register(RegRequest regRequest);

    Boolean checkUsername(CheckUname checkUname);

    User login(LoginRequest loginRequest);
}
