package com.example.wisper_one.websocket.chat.handler;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat.POJO.ChatMessageEntity;
import com.example.wisper_one.websocket.chat.mapper.ChatMessageMapper;
import com.example.wisper_one.websocket.util.GlobalWsSessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

        R =
        * 红色节点

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
    @Resource
    private UserMapper userMapper;

    private final ObjectMapper mapper = new ObjectMapper();
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println(session);
        String userId = (String) session.getAttributes().get("userId");
        String userCode = userMapper.selectCodeByUname(userId);

        if (userCode == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("no userCode"));
            System.out.println("用户上线失败userCode为空");
            return;
        }

        //顶号(我的老代码)
//        if (old != null && old.isOpen()) {
//            old.sendMessage(new TextMessage("账号已经在别处登录"));
//            old.close();
//        }

        List<ChatMessageEntity> message = chatMessageMapper.checkMessageRead(userCode);

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


        GlobalWsSessionManager.add(userCode, session);

        System.out.println("上线了" + userCode);


    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String fromUserId = (String) session.getAttributes().get("userId");
        String fromUserCode = userMapper.selectCodeByUname(fromUserId);


        String redisKey = "login:token:" + fromUserCode;
        Boolean exists = redisTemplate.hasKey(redisKey);
        if (!Boolean.TRUE.equals(exists)) {

            ObjectNode expireMsg = mapper.createObjectNode();
            expireMsg.put("error", "登录已过期，请重新登录");

            session.sendMessage(new TextMessage(expireMsg.toString()));

            // 从全局管理器移除
            GlobalWsSessionManager.remove(fromUserCode, session);

            // 强制关闭
            session.close(CloseStatus.POLICY_VIOLATION.withReason("login expired"));

            return;
        }

        JsonNode json = mapper.readTree(message.getPayload());
        if (!json.has("to") || !json.has("msg")) {
            session.sendMessage(new TextMessage("消息格式错误"));
            return;
        }

        String toUserCode = json.get("to").asText();
        String content = json.get("msg").asText();
        String type = json.has("type") ? json.get("type").asText() : "text";





        if (content.isEmpty() || content.length() > 2000) {
            session.sendMessage(new TextMessage("消息内容不合法"));
            return;
        }

        // 保存消息到数据库
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setSender(fromUserCode);
        String toresult = userMapper.selectUnameByCode(toUserCode);
        if (toresult==null||toresult.isEmpty()) {
            ObjectNode errorResp = mapper.createObjectNode();
            errorResp.put("error", "发送失败：该用户不存在");
            session.sendMessage(new TextMessage(errorResp.toString()));
            return;
        }
        chatMessageEntity.setReceiver(toUserCode);
        chatMessageEntity.setContent(content);
        chatMessageEntity.setType(type);
        chatMessageEntity.setCreateTime(LocalDateTime.now());
        chatMessageEntity.setRevoked(0);
        chatMessageEntity.setRevokedAt(null);

        int rows = chatMessageMapper.insert(chatMessageEntity);
        if (rows != 1) {
            session.sendMessage(new TextMessage("实时聊天插入失败"));
//            throw new BusinessException("实时聊天插入失败");
        }

        // 通过 GlobalWsSessionManager 发送消息
        Set<WebSocketSession> targetSessions = GlobalWsSessionManager.getSessions(toUserCode);
        if (targetSessions != null && !targetSessions.isEmpty()) {
            ObjectNode resp = mapper.createObjectNode();
            resp.put("from", fromUserCode);
            resp.put("msg", content);
            resp.put("type", type);

            for (WebSocketSession s : targetSessions) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(resp.toString()));
                }
            }
        } else {
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

        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            String userCode = userMapper.selectCodeByUname(userId);
            GlobalWsSessionManager.remove(userCode, session);



        }


        System.out.println("用户下线：" + status);
    }
}
