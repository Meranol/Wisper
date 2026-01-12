package com.example.wisper_one.Login.DTO.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 * File: RegRequest
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
public class RegRequestDto {
    @NotBlank(message = "用户名不能为空")
    @Size(min  =3,max=20,message="用户名长度3-20位")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$", message = "用户名只能包含字母、数字、下划线和中文字符")
    private String username;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱最多100字符")

    private String email;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度6-50位")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "密码必须包含字母和数字")
    private String password;


   public RegRequestDto() {}

    public RegRequestDto(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }










}
