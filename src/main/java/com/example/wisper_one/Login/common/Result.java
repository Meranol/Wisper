package com.example.wisper_one.Login.common;

import com.example.wisper_one.utils.ResultCode;

import java.io.Serializable;
/**
 * File: Result
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    private Result() {}

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    //只返回结果用
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS,"success",null);
    }
    //返回查询数据用
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS, "success", data);
    }
    //返回操作数据用
    public static <T> Result<T> success(String msg,T data) {
        return new Result<>(ResultCode.SUCCESS,msg,data);
    }

    //失败返回
    public static <T> Result<T> failure(int code ,String msg) {
        return new Result<>(code,msg,null);
    }
    public static <T> Result<T> failure(String msg) {
        return new Result<>(ResultCode.FAILURE,msg,null);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
