package com.example.wisper_one.interceptor;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.utils.ResultCode;
import com.example.wisper_one.utils.jwt.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * File: SimpleJwtFilter
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
@Component
public class SimpleJwtFilter extends OncePerRequestFilter {

    private static final List<String> WHITE_LIST = List.of(
            "/user/login",
            "/user/register",
            "/user/checkusername"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        //放行WebSocket 握手
        if (path.startsWith("/ws/")) {
            chain.doFilter(request, response);
            return;
        }

        //放行浏览器OPTIONS请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        //白名单赛选
        if (WHITE_LIST.contains(path)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            writeUnauthorized(response, "未携带token");
            return;
        }

        String token = authHeader.substring(7);

        //验token
        Result<String> verifyResult = JwtTokenUtil.verifyToken(token);
        if (verifyResult.getCode() != ResultCode.SUCCESS) {
            writeUnauthorized(response, "token无效或已过期");
            return;
        }

        //写入SpringSecurity
        String username = verifyResult.getData();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 我他妈直接放行
        chain.doFilter(request, response);
    }


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private void writeUnauthorized(HttpServletResponse response, String message)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Result<?> result = Result.failure(ResultCode.UNAUTHORIZED, message);

        String json = OBJECT_MAPPER.writeValueAsString(result);
        response.getWriter().write(json);
    }

}
