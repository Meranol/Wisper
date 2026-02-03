package com.example.wisper_one.websocket.CloseStatus;

import org.springframework.web.socket.CloseStatus;

public class WsCloseStatus {

    public static final CloseStatus KICKED =
            new CloseStatus(4001, "账号已被踢出");

    public static final CloseStatus TOKEN_EXPIRED =
            new CloseStatus(4002, "登录已过期");

    private WsCloseStatus() {}
}
