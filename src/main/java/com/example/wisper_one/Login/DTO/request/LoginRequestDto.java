package com.example.wisper_one.Login.DTO.request;

import javax.validation.constraints.NotBlank;
/**
 * File: LoginRequest
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
public class LoginRequestDto {

    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "请输入密码")
    private String password;

    public LoginRequestDto() {}


    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
