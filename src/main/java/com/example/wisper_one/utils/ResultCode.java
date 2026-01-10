package com.example.wisper_one.utils;
/**
 * File: ResultCode
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
public interface ResultCode {
    int SUCCESS = 200;

    int PARAM_ERROR = 400;
    int UNAUTHORIZED = 401;
    int FORBIDDEN = 403;
    int NOT_FOUND = 404;

    int BUSINESS_ERROR = 1001;
    int USER_NOT_EXIST = 1002;
    int USERNAME_OR_PASSWORD_ERROR = 1003;

    int SYSTEM_ERROR = 500;
    int FAILURE = -1;
}
