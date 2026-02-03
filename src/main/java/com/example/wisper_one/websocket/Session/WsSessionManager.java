package com.example.wisper_one.websocket.Session;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File: WsSessionManager
 * Author: [周玉诚]
 * Date: 2026/2/4
 * Description:
 */
public class WsSessionManager {
    private static final Map<String, WebSocketSession> PRIVATE_SESSIONS = new ConcurrentHashMap<>();//WebSocket多线程环境必要用ConcurrentHashMap


    public static void add(String usercode, WebSocketSession session) {
        PRIVATE_SESSIONS.put(usercode, session);
    }

    public static WebSocketSession get(String usercode) {
        return PRIVATE_SESSIONS.get(usercode);
    }

    public static void remove(String usercode) {
        PRIVATE_SESSIONS.remove(usercode);
    }




}
