package com.example.wisper_one.Login.mapper;

import com.example.wisper_one.Login.POJO.UserPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * File: UserMapper
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@Mapper
public interface UserMapper {

    // 插入用户
    int insertUser(UserPo user);

    // 根据用户名查询用户
    UserPo selectUserByUsername(@Param("username") String username);

    // 根据邮箱查询用户
    String selectUserByEmail(@Param("email") String email);

    //更具用户名查询user_code
    String selectCodeByUname(@Param("Uname") String uname);

    // 更新用户（登录时间、状态等）
    int updateUser(UserPo user);

    // 检查用户名是否存在
    int existsByUsername(@Param("username") String username);

    // 检查邮箱是否存在
    int existsByEmail(@Param("email") String email);
}
