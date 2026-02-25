package com.example.wisper_one.userUpdata.changeuseravatarurl.mapper;

import com.example.wisper_one.userUpdata.changeuseravatarurl.DTO.ChangeUserAvatarurlDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * File: ChangeUserAvatarurlMapper
 * Author: [周玉诚]
 * Date: 2026/2/24
 * Description:
 */
@Mapper
public interface ChangeUserAvatarurlMapper {

    int changeUserAvatarurlDto(ChangeUserAvatarurlDto changeUserAvatarurlDto);

}
