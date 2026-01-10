//package com.example.wisper_one.interceptor;
//
//import com.example.wisper_one.Login.common.Result;
//import com.example.wisper_one.utils.Exception.BusinessException;
//import com.example.wisper_one.utils.ResultCode;
//import com.example.wisper_one.utils.jwt.JwtTokenUtil;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class JwtAuthInterceptor implements HandlerInterceptor {
//    private static final List<String> WHITELIST = Arrays.asList(
//            "/user/login",
//            "/user/register"
//    );
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String path = request.getServletPath();
//        System.out.println("拦截路径: " + path);
//
//        if (WHITELIST.contains(path)) {
//            System.out.println("白名单路径，直接放行");
//            return true;
//        }
//
//        String authHeader = request.getHeader("Authorization");
//        System.out.println("Authorization Header: " + authHeader);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            System.out.println("Token缺失或格式错误");
//            throw new BusinessException("缺少token");
//        }
//
//        String token = authHeader.substring(7);
//        System.out.println("提取的Token: " + token.substring(0, Math.min(token.length(), 20)) + "...");
//
//        Result<String> verifyResult = JwtTokenUtil.verifyToken(token);
//        System.out.println("验证结果: " + verifyResult);
//
//        if (verifyResult.getCode() != ResultCode.SUCCESS) {
//            System.out.println("Token验证失败: " + verifyResult.getMsg());
//            throw new BusinessException(verifyResult.getMsg());
//        }
//
//        String username = verifyResult.getData();
//        System.out.println("验证成功，设置username: " + username);
//
//        request.setAttribute("username", username);
//        return true;
//    }
//}