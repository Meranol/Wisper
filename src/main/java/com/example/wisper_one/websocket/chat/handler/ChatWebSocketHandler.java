package com.example.wisper_one.websocket.chat.handler;

import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat.POJO.ChatMessageEntity;
import com.example.wisper_one.websocket.chat.mapper.ChatMessageMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File: ChatWebSocketHandler
 * Author: [周玉诚]
 * Date: 2026/1/11
 * Description:
 */

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    /*      10B
           /   \
         5R     20B
        / \     / \
      3B   7B 15R  25B*/

        /*
        * B = 黑色节点

        R = 红色节点

        查找 7：

        根 10 → 7 < 10 → 左子树

        左子树 5 → 7 > 5 → 右子树

        找到 7

        查找只用了 3 步，时间复杂度 O(log n)
    *
    * */

    // HashMap的遍历顺序不是插入顺序是根据key的哈希值+桶结构来决定的


    /**
     * WebSocketSession 常用方法
     * 方法	            作用
     * sendMessage(WebSocketMessage<?> message)	发送消息到客户端
     * close() 或 close(CloseStatus status)	关闭这个 WebSocket 连接
     * isOpen()	判断连接是否还打开
     * getAttributes()	获取在握手阶段设置的属性，例如 userId
     * getId()	session 的唯一 id（Spring 自动生成）
     * getRemoteAddress()	获取客户端 IP 和端口
     **/

    @Resource
    private ChatMessageMapper chatMessageMapper;

    private static final Map<String, WebSocketSession> ONLINE_USERS = new ConcurrentHashMap<>(); //ConcurrentHashMap 是线程安全的 HashMap 版本

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println(session);
        String userId = (String) session.getAttributes().get("userId");

        if (userId == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("no userId"));
            System.out.println("用户上线失败userid为空");
            return;
        }

        WebSocketSession old = ONLINE_USERS.put(userId, session);

        if (old != null && old.isOpen()) {
            old.sendMessage(new TextMessage("账号已经在别处登录"));
            old.close();
        }

        List<ChatMessageEntity> message = chatMessageMapper.checkMessageRead(userId);

        ObjectMapper mapper = new ObjectMapper();
        for (ChatMessageEntity msg : message) {
            ObjectNode node = mapper.createObjectNode();
            node.put("from", msg.getSender());
            node.put("msg", msg.getContent());
            node.put("type", msg.getType());

            session.sendMessage(new TextMessage(node.toString()));

            // 标记为已读
            chatMessageMapper.updateMessageReadState(msg.getId());
        }






        System.out.println("上线了" + userId);


    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        //当前用户
        String fromUserId = (String) session.getAttributes().get("userId");
        // 解析消息
        ObjectMapper mapper = new ObjectMapper();

        JsonNode json = mapper.readTree(message.getPayload());

        if (!json.has("to") || !json.has("msg")) {
            session.sendMessage(new TextMessage("消息格式错误"));
            return;
        }

        String toUserId = json.get("to").asText();
        String content = json.get("msg").asText();
        String type = json.has("type") ? json.get("type").asText() : "text"; // 默认是文本


        if (content.isEmpty() || content.length() > 2000) {
            session.sendMessage(new TextMessage("消息内容不合法"));
            return;
        }
        //找到接收方session
        WebSocketSession targetSession = ONLINE_USERS.get(toUserId);




        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setSender(fromUserId);
        chatMessageEntity.setReceiver(toUserId);
        chatMessageEntity.setContent(content);
        chatMessageEntity.setType(type);
        chatMessageEntity.setRevoked(0);
        chatMessageEntity.setCreateTime(LocalDateTime.now());
        chatMessageEntity.setRevokedAt(null);


        ObjectNode resp = mapper.createObjectNode();
        resp.put("from", fromUserId);
        resp.put("msg", content);
        resp.put("type", type);


        int rows = chatMessageMapper.insert(chatMessageEntity);
        if (rows != 1) {
            throw new BusinessException("实时聊天插入失败");
        }
        //发给对方
        if (targetSession != null && !targetSession.isOpen()) {
            targetSession.sendMessage(new TextMessage(resp.toString()));
        }else {
            System.out.println("对方不在线，消息已保存数据库");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        String removeKey = null;
//
//        for (Map.Entry<String, WebSocketSession> entry : ONLINE_USERS.entrySet()) {
//
//            WebSocketSession storedSession = entry.getValue();
//
//            if (storedSession.equals(session)) {
//                removeKey = entry.getKey();
//                break;
//            }
//        }
//
//        if (removeKey != null) {
//            ONLINE_USERS.remove(removeKey);
//            System.out.println("用户下线：" + removeKey);
//        }

        ONLINE_USERS.entrySet().removeIf(
                entry -> entry.getValue().equals(session)
        );


        System.out.println("用户下线：" + status);
    }
}
