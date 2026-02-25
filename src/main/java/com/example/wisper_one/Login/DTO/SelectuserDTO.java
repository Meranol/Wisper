package com.example.wisper_one.Login.DTO;

import lombok.Data;

/**
 * File: SelectuserDTO
 * Author: [周玉诚]
 * Date: 2026/2/24
 * Description:
 */


@Data
public class SelectuserDTO {

    private Long id;
    private String username;
    private String nickname;
    private String avatarUrl;
    private String publicId;
    private String email;
    private Integer vip;
}