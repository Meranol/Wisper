package com.example.wisper_one.userUpdata.changeuseravatarurl.service.servicempl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.userUpdata.changeuseravatarurl.DTO.ChangeUserAvatarurlDto;
import com.example.wisper_one.userUpdata.changeuseravatarurl.mapper.ChangeUserAvatarurlMapper;
import com.example.wisper_one.userUpdata.changeuseravatarurl.service.ChangeUserAvatarurlSerivce;
import com.example.wisper_one.utils.Exception.BusinessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * File: ChangeUserAvatarurlSerivce
 * Author: [周玉诚]
 * Date: 2026/2/24
 * Description:
 */
@Service
public class ChangeUserAvatarurlSerivcempl implements ChangeUserAvatarurlSerivce {
    @Resource
    private ChangeUserAvatarurlMapper mapper;
    @Resource
    private UserMapper userMapper;
    @Override
    public ChangeUserAvatarurlDto changeUserAvatarurlDto(ChangeUserAvatarurlDto changeUserAvatarurlDto) {
        String username = (String) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        String handlerCode = userMapper.selectCodeByUname(username);
        changeUserAvatarurlDto.setPublic_id(handlerCode);

        int rows = mapper.changeUserAvatarurlDto(changeUserAvatarurlDto);
        if (rows != 1) {
            throw new BusinessException("修改头像失败，用户不存在或未更新");
        }
        return changeUserAvatarurlDto;
    }
}
