package com.example.wisper_one.websocket.util;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File: GlobalWsSessionManager
 * Author: [周玉诚]
 * Date: 2026/2/4
 * Description:
 */
public class GlobalWsSessionManager {
    public static final Map<String, Set<WebSocketSession>> USER_SESSIONS
            = new ConcurrentHashMap<>();


    public static void add(String userCode, WebSocketSession session) {
        USER_SESSIONS
                .computeIfAbsent(userCode, k -> ConcurrentHashMap.newKeySet())
                .add(session);
    }

    public static void remove(String userCode, WebSocketSession session) {
        Set<WebSocketSession> set = USER_SESSIONS.get(userCode);
        if (set != null) {
            set.remove(session);
            if (set.isEmpty()) {
                USER_SESSIONS.remove(userCode);
            }
        }
    }

    public static void kick(String userCode, CloseStatus status) {
        Set<WebSocketSession> set = USER_SESSIONS.remove(userCode);
        if (set == null) return;

        for (WebSocketSession session : set) {
            if (session.isOpen()) {
                try {
                    session.close(status);
                } catch (Exception ignored) {}
            }
        }
    }


    public static Set<WebSocketSession> getSessions(String userCode) {
        Set<WebSocketSession> set = USER_SESSIONS.get(userCode);
        if (set == null) {
            return Set.of();
        }
        return set;
    }


    public static Set<String> getOnlineUserCodes() {
        return USER_SESSIONS.keySet();
    }



}
