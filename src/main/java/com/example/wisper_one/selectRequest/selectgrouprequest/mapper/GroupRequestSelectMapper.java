package com.example.wisper_one.selectRequest.selectgrouprequest.mapper;

import com.example.wisper_one.finduserlist.POJO.SelectUserFriendListDTO;
import com.example.wisper_one.selectRequest.selectgrouprequest.DTO.SelectGroupRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * File: GroupRequestSelectMapper
 * Author: [周玉诚]
 * Date: 2026/2/25
 * Description:
 */
@Mapper
public interface GroupRequestSelectMapper {

    List<SelectGroupRequestDTO>selectGroupRequest(@Param("userCode") String userCode);


}
