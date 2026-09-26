package com.example.wisper_one.usercontroller.usercode.mapper;

import com.example.wisper_one.usercontroller.usercode.UserCodeSeq;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * File: UserCodeMapper
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@Mapper
public interface UserCodeMapper {

    UserCodeSeq findByYear(@Param("year") int year);
    @Select("SELECT * FROM user_code_seq WHERE year = #{year} FOR UPDATE")
    UserCodeSeq findByYearForUpdate(@Param("year") int year);
    int insert(UserCodeSeq seq);

    int update(UserCodeSeq seq);
}
