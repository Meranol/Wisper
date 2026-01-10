package com.example.wisper_one.Login.DTO.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 * File: CheckUname
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
public class CheckUname {
    @NotBlank(message = "查询的用户名不能为空")
    @Size(min  =3,max=20,message="用户名长度3-20位")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$", message = "用户名只能包含字母、数字、下划线和中文字符")
    private String uname;


    public CheckUname() {}


    public CheckUname(String uname) {
        this.uname = uname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }





}
