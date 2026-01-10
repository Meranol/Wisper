package com.example.wisper_one.Login.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
/**
 * File: User
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@Data
public class User {

    private Integer id;
    private String publicId;
    private String username;

    @JsonIgnore
    private String passwordHash;

    @JsonIgnore
    private String salt;

    private String email;
    private String nickname;
    private String avatarUrl;

    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
}
