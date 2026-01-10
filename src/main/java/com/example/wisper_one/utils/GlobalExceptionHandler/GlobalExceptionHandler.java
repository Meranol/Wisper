package com.example.wisper_one.utils.GlobalExceptionHandler;


import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.Login.service.servicempl.UserServicelmpl;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.utils.ResultCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException;

import java.util.stream.Collectors;
/**
 * File: GlobalExceptionHandler
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(RuntimeException e) {
        return Result.failure(ResultCode.PARAM_ERROR, e.getMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class) //处理RequestParam @Valid DTO
    public Result<?> handleConstraintViolation(ConstraintViolationException e) {
        String msg = e.getConstraintViolations()
                .stream()
                .map(v -> v.getMessage())
                .collect(Collectors.joining("; "));
        return Result.failure(ResultCode.PARAM_ERROR, msg);
    }
    // 2️⃣ GET + @Valid DTO
    @ExceptionHandler(BindException.class)
    public Result<?> handleBind(BindException e) {
        String msg = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return Result.failure(ResultCode.PARAM_ERROR, msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //处理@RequestBody @Valid DTO
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return Result.failure(ResultCode.PARAM_ERROR, errorMsg);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        e.printStackTrace();
        return Result.failure(ResultCode.SYSTEM_ERROR, "服务器内部错误");
    }
}
