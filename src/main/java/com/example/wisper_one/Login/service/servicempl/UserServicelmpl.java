package com.example.wisper_one.Login.service.servicempl;

import com.example.wisper_one.Login.DTO.CheckUnameDto;
import com.example.wisper_one.Login.DTO.LoginRequestDto;
import com.example.wisper_one.Login.DTO.RegRequestDto;
import com.example.wisper_one.Login.DTO.SelectuserDTO;
import com.example.wisper_one.Login.POJO.UserPo;
import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.Login.service.UserService;
import com.example.wisper_one.Login.usercode.service.UserCodeService;
import com.example.wisper_one.useraccount.PO.UserAccountPO;
import com.example.wisper_one.useraccount.mapper.UserAccountMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
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
    @Resource
    private UserAccountMapper accountMapper;
    @Transactional(rollbackFor = BusinessException.class)
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

        user.setAvatarUrl(regRequest.getAvatarUrl());
        user.setUsername(regRequest.getUsername());
        user.setEmail(regRequest.getEmail());
        user.setPublicId(userCodeService.generateUserCode());
        user.setPasswordHash(passwordEncoder.encode(regRequest.getPassword()));
        user.setSalt(user.getPasswordHash());
        user.setNickname(regRequest.getUsername());
        user.setStatus(1);
        user.setVip(0);
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        String tempAvatarUrl = regRequest.getAvatarUrl(); // 前端传来的预上传头像URL
        String finalAvatarUrl = null;
        try {
            if (tempAvatarUrl != null && !tempAvatarUrl.trim().isEmpty()) {
                String filename = tempAvatarUrl.substring(tempAvatarUrl.lastIndexOf("/") + 1);
                File tempFile = new File("E:/wisperimage/temp/" + filename);
                if (tempFile.exists()) {
                    File finalDir = new File("E:/wisperimage/user/");
                    if (!finalDir.exists()) finalDir.mkdirs();

                    File finalFile = new File(finalDir, filename);
                    if (tempFile.renameTo(finalFile)) {
                        finalAvatarUrl = "/uploads/user/" + filename;
                    } else {
                        throw new BusinessException("头像移动失败");
                    }
                }
            }

            user.setAvatarUrl(finalAvatarUrl);

            int rows = userMapper.insertUser(user);
            if (rows != 1) {
                // 注册失败删除已移动头像
                if (finalAvatarUrl != null) {
                    File file = new File("E:/wisperimage/user/" + finalAvatarUrl.substring(finalAvatarUrl.lastIndexOf("/") + 1));
                    if (file.exists()) file.delete();
                }
                throw new BusinessException("注册插入失败");
            }
            UserAccountPO accountPO = new UserAccountPO();
            accountPO.setUserCode(user.getPublicId());  // 关联 users.public_id
            accountPO.setBalance(BigDecimal.ZERO);      // 初始余额
            accountPO.setVersion(0);                    // 乐观锁初始版本
            accountPO.setCreatedAt(now);
            accountPO.setUpdatedAt(now);

            int accRows = accountMapper.insert(accountPO);
            // 插入流水记录

            if (accRows != 1) {
                throw new BusinessException("账户初始化失败");
            }

            int accouint_record = accountMapper.insertAccountRecord(
                    user.getPublicId(),        // 用户
                    BigDecimal.ZERO,           // 变动金额
                    "注册初始化",               // 类型
                    null,                      // 无关联ID
                    now                        // 创建时间
            );
            if (accouint_record != 1) {
                throw new BusinessException("账户账户流水账单初始化失败");
            }

        } catch (Exception e) {
            // 出现异常 删除 temp 文件
            if (tempAvatarUrl != null && !tempAvatarUrl.trim().isEmpty()) {
                File file = new File("E:/wisperimage/temp/" + tempAvatarUrl.substring(tempAvatarUrl.lastIndexOf("/") + 1));
                if (file.exists()) file.delete();
            }
            throw e;
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

    @Override
    public SelectuserDTO getUserByPublicId(String publicId) {
        System.out.println("publicId: " + publicId);
        UserPo user = userMapper.selectUserByPublicId(publicId);

        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        SelectuserDTO dto = new SelectuserDTO();
        dto.setPublicId(user.getPublicId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setEmail(user.getEmail());
        dto.setVip(user.getVip());

        return dto;
    }


}

