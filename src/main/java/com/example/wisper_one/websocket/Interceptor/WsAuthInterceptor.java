package com.example.wisper_one.websocket.Interceptor;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.utils.ResultCode;
import com.example.wisper_one.utils.jwt.JwtTokenUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * File: WsAuthInterceptor
 * Author: [周玉诚]
 * Date: 2026/1/11
 * Description: ws拦截握手拦截器
 */

@Configuration
@EnableWebSocket
public class WsAuthInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = null;

        if (request instanceof ServletServerHttpRequest) {//instanceof作用为判断这里的request是否是ServletServerHttpRequest的子类
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

            // 先从 query
            String query = servletRequest.getQueryString();
            if (query != null && query.startsWith("token=")) token = query.substring(6);

            // 如果没有再从header
            if (token == null) {
                String auth = servletRequest.getHeader("Authorization");
                if (auth != null && auth.startsWith("Bearer ")) token = auth.substring(7);
            }
        }

        if (token == null) return false;

        Result<String> result = JwtTokenUtil.verifyToken(token);
        if (result.getCode() != ResultCode.SUCCESS) return false;

        attributes.put("userId", result.getData());
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
