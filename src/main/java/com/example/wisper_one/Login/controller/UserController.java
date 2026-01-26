package com.example.wisper_one.Login.controller;

import com.example.wisper_one.Login.DTO.request.CheckUnameDto;
import com.example.wisper_one.Login.DTO.request.LoginRequestDto;
import com.example.wisper_one.Login.DTO.request.RegRequestDto;
import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.Login.POJO.UserPo;
import com.example.wisper_one.Login.service.UserService;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.utils.jwt.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
/**
 * File: UserController
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Result<UserPo> register(@RequestBody RegRequestDto regRequest) {
        UserPo user = userService.register(regRequest);
        return Result.success("注册成功",user);
    }


    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginRequestDto loginRequest) {

        UserPo user = userService.login(loginRequest);
        String token = JwtTokenUtil.generateToken(user.getUsername(),user.getPublicId(), 200000 * 960000000 * 6000000 * 1000000L);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickname());
        data.put("avatarUrl", user.getAvatarUrl());


        return Result.success("登录成功",data);
    }

    @GetMapping("/checkusername")
    public Result<Boolean> checkUsername(@Valid CheckUnameDto checkUname) {
        boolean isAvailable = userService.checkUsername(checkUname);

        String message = isAvailable ? "用户名可用" : "用户名已存在";
        return Result.success(message, isAvailable);
    }

    @GetMapping("/profile")
    public Result<?> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("未登录");
        }

        String username = (String) authentication.getPrincipal();
        return Result.success("当前用户", username);
    }



    @PostMapping("/token")
    public Result<Map<String, Object>> token(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);

        Result<String> verify = JwtTokenUtil.verifyToken(token);
        Map<String, Object> result = new HashMap<>();
        result.put("verifyResult", verify);
        result.put("token", token);
        return Result.success("测试完成", result);
    }





}
