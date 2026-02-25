package com.example.wisper_one.userUpdata.changeuseravatarurl.DTO;

import lombok.Data;

/**
 * File: ChangeUserAvatarurlDto
 * Author: [周玉诚]
 * Date: 2026/2/24
 * Description: 前端传入要修改的头像URL和用户publicId
 */
@Data
public class ChangeUserAvatarurlDto {

    private String public_id;
    private String avatarUrl;
}
