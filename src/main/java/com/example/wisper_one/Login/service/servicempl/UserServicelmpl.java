package com.example.wisper_one.Login.service.servicempl;

import com.example.wisper_one.Login.DTO.request.CheckUnameDto;
import com.example.wisper_one.Login.DTO.request.LoginRequestDto;
import com.example.wisper_one.Login.DTO.request.RegRequestDto;
import com.example.wisper_one.Login.POJO.UserPo;
import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.Login.service.UserService;
import com.example.wisper_one.Login.usercode.service.UserCodeService;
import com.example.wisper_one.utils.Exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
/**
 * File: UserServicelmpl
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@Service
public class UserServicelmpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserCodeService userCodeService;

    @Override
    public UserPo register(RegRequestDto regRequest) {
        if (userMapper.existsByUsername(regRequest.getUsername()) > 0) {
            throw new BusinessException("用户名已存在");
        }
        if (regRequest.getEmail() != null && !regRequest.getEmail().trim().isEmpty()
                && userMapper.existsByEmail(regRequest.getEmail()) > 0) {
            throw new BusinessException("邮箱已被占用");
        }

        UserPo user = new UserPo();
        user.setUsername(regRequest.getUsername());
        user.setEmail(regRequest.getEmail());
        user.setPublicId(userCodeService.generateUserCode());
        user.setPasswordHash(passwordEncoder.encode(regRequest.getPassword()));
        user.setSalt(user.getPasswordHash());
        user.setNickname(regRequest.getUsername());
        user.setStatus(1);
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

      int rows =   userMapper.insertUser(user);

        if (rows !=1) {
            throw new BusinessException("注册插入失败");
        }
        return user;
    }

    @Override
    public Boolean checkUsername(CheckUnameDto checkUname) {
        return userMapper.existsByUsername(checkUname.getUname()) == 0;
    }

    @Override
    public UserPo login(LoginRequestDto loginRequest) {
        UserPo user = userMapper.selectUserByUsername(loginRequest.getUsername());

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("该用户已禁用");
        }

        user.setLastLoginAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateUser(user);

        return user;
    }
}
