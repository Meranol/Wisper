package com.example.wisper_one.administrator.kick.service.servicempl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.administrator.kick.DTO.KickDTO;
import com.example.wisper_one.administrator.kick.service.Kickservice;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat_group.util.GlobalWsSessionManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * File: Kickservicempl
 * Author: [周玉诚]
 * Date: 2026/2/3
 * Description:
 */
@Service
public class Kickservicempl implements Kickservice {


    @Resource
    UserMapper userMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public boolean kick(KickDTO kickDTO) {
        String username = (String) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        String fromUserCode = userMapper.selectCodeByUname(username);
        String vip = userMapper.selectVipByCode(fromUserCode);
        if (!Objects.equals(vip, "1")) {
             throw new BusinessException("你不是vip用户");
        }

        String redisKey = "login:token:" + kickDTO.getPublic_id();

        boolean tokenDeleted = false;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            redisTemplate.delete(redisKey);
            tokenDeleted = true;
        }


        String userCode = kickDTO.getPublic_id();
        if (userCode != null) {
            GlobalWsSessionManager.kick(userCode,
                    org.springframework.web.socket.CloseStatus.POLICY_VIOLATION.withReason("管理员强制下线"));
        }
        return tokenDeleted || userCode != null;
    }
}
