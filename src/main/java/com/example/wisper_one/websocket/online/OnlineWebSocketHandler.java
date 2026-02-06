package com.example.wisper_one.websocket.online;

import com.example.wisper_one.websocket.util.GlobalWsSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;

@Component
public class OnlineWebSocketHandler extends TextWebSocketHandler {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userCode = (String) session.getAttributes().get("userCode");
        if (userCode != null) {
            GlobalWsSessionManager.add(userCode, session);
            broadcast("ONLINE", userCode);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userCode = (String) session.getAttributes().get("userCode");
        if (userCode != null) {
            GlobalWsSessionManager.remove(userCode, session);
            if (GlobalWsSessionManager.getSessions(userCode).isEmpty()) {
                broadcast("OFFLINE", userCode);
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    }

    private static void broadcast(String event, String userCode) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("event", event);
        msg.put("userCode", userCode);
        TextMessage message;
        try {
            message = new TextMessage(msg.toString());
        } catch (Exception e) {
            return;
        }

        GlobalWsSessionManager.USER_SESSIONS.forEach((code, sessions) -> {
            for (WebSocketSession s : sessions) {
                try {
                    if (s.isOpen()) s.sendMessage(message);
                } catch (Exception ignored) {}
            }
        });
    }

    // 被管理员踢时调用
    public static void broadcastOffline(String userCode) {
        broadcast("OFFLINE", userCode);
    }
}
