package com.example.wisper_one.finduserlist.mapper;

import com.example.wisper_one.finduserlist.POJO.SelectUserFriendListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * File: SelectUserFriendListMapper
 * Author: [周玉诚]
 * Date: 2026/2/10
 * Description:
 */
@Mapper
public interface SelectUserFriendListMapper {

    List<SelectUserFriendListDTO> selectFriendListWithLastMsg(@Param("userCode") String userCode);

}
