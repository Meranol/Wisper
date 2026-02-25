package com.example.wisper_one.selectRequest.selectgrouprequest.service.servicempl;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.selectRequest.selectgrouprequest.DTO.SelectGroupRequestDTO;
import com.example.wisper_one.selectRequest.selectgrouprequest.mapper.GroupRequestSelectMapper;
import com.example.wisper_one.selectRequest.selectgrouprequest.service.GroupRequestSelectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * File: GroupRequestSelectServicelmpl
 * Author: [周玉诚]
 * Date: 2026/2/25
 * Description:
 */
@Service
public class GroupRequestSelectServicelmpl implements GroupRequestSelectService {

    @Resource
    UserMapper userMapper;
    @Resource
    private GroupRequestSelectMapper groupRequestSelectMapper;
    @Override
    public List<SelectGroupRequestDTO> groupRequestSelect() {

        String username = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String currentUserCode = userMapper.selectCodeByUname(username);

        return groupRequestSelectMapper.selectGroupRequest(currentUserCode);
    }
}
