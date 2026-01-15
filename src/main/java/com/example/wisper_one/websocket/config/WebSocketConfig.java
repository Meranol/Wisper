package com.example.wisper_one.websocket.config;

import com.example.wisper_one.websocket.Interceptor.WsAuthInterceptor;
import com.example.wisper_one.websocket.chat.handler.ChatWebSocketHandler;
import com.example.wisper_one.websocket.chat_group.handler.GroupChatWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * File: WebSocketConfig
 * Author: [周玉诚]
 * Date: 2026/1/11
 * Description:
 */


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;
    @Autowired
    private GroupChatWebSocketHandler groupChatWebSocketHandler;
    @Autowired
    private WsAuthInterceptor wsAuthInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(chatWebSocketHandler, "/ws/chat").addInterceptors(wsAuthInterceptor)//添加拦截器解析token获取用户信息
                .setAllowedOriginPatterns("*"); // 允许跨域

        registry.addHandler(groupChatWebSocketHandler, "/ws/group")
                .addInterceptors(wsAuthInterceptor)
                .setAllowedOriginPatterns("*");
    }
}