package com.example.wisper_one.websocket.config;

import com.example.wisper_one.websocket.Interceptor.WsAuthInterceptor;
import com.example.wisper_one.websocket.chat.handler.ChatWebSocketHandler;
import com.example.wisper_one.websocket.chat_group.handler.GroupChatWebSocketHandler;
import com.example.wisper_one.websocket.online.OnlineWebSocketHandler;
import com.example.wisper_one.websocket.online.OnlineWsInterceptor;
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
    private OnlineWebSocketHandler onlineWebSocketHandler;

    @Autowired
    private WsAuthInterceptor wsAuthInterceptor;

    @Autowired
    private OnlineWsInterceptor onlineWsInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 聊天
        registry.addHandler(chatWebSocketHandler, "/ws/chat")
                .addInterceptors(wsAuthInterceptor)
                .setAllowedOriginPatterns("*");

        registry.addHandler(groupChatWebSocketHandler, "/ws/group")
                .addInterceptors(wsAuthInterceptor)
                .setAllowedOriginPatterns("*");

        // 在线状态
        registry.addHandler(onlineWebSocketHandler, "/ws/online")
                .addInterceptors(onlineWsInterceptor)
                .setAllowedOriginPatterns("*");
    }
}