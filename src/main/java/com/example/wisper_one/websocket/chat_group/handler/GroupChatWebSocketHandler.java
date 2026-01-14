package com.example.wisper_one.websocket.chat_group.handler;

import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMessageEntity;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMapper;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMessageMapper;
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
 * File: GroupChatWebSocketHandler
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description:
 */

@Component
public class GroupChatWebSocketHandler extends TextWebSocketHandler {

    // key: groupId, value: map<userId, session>
    private static final Map<Long, Map<String, WebSocketSession>> GROUP_ONLINE_USERS = new ConcurrentHashMap<>();

    @Resource
    private ChatGroupMapper chatGroupMapper;

    @Resource
    private ChatGroupMessageMapper chatGroupMessageMapper;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        String groupId = (String) session.getAttributes().get("groupId");

        if (userId == null || groupId == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("userId or groupId missing"));
            return;
        }

        // 保存在线状态
        GROUP_ONLINE_USERS.putIfAbsent(Long.valueOf(groupId), new ConcurrentHashMap<>());
        GROUP_ONLINE_USERS.get(groupId).put(userId, session);

        // 获取离线未读消息
        List<ChatGroupMessageEntity> unreadMessages = chatGroupMessageMapper.selectUnreadMessages(Long.valueOf(groupId), userId);

        for (ChatGroupMessageEntity msg : unreadMessages) {
            ObjectNode node = mapper.createObjectNode();
            node.put("from", msg.getSenderId());
            node.put("msg", msg.getContent());
            node.put("type", msg.getType());
            node.put("groupId", msg.getGroupId());

            session.sendMessage(new TextMessage(node.toString()));

            // 标记已读
            chatGroupMessageMapper.updateMessageReadState(msg.getId(), userId);
        }

        System.out.println("用户上线群聊: " + userId + ", groupId=" + groupId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        String groupId = (String) session.getAttributes().get("groupId");

        ObjectNode json = (ObjectNode) mapper.readTree(message.getPayload());
        String content = json.get("msg").asText();
        String type = json.has("type") ? json.get("type").asText() : "text";

        if (content.isEmpty() || content.length() > 2000) {
            session.sendMessage(new TextMessage("消息内容不合法"));
            return;
        }

        // 校验用户是否在群里
        ChatGroupMemberEntity member = chatGroupMapper.selectMember(groupId, userId);
        if (member == null) {
            session.sendMessage(new TextMessage("你不在这个群里"));
            return;
        }


        ChatGroupMessageEntity msgEntity = new ChatGroupMessageEntity();
        msgEntity.setGroupId(Long.valueOf(groupId));
        msgEntity.setSenderId(userId);
        msgEntity.setContent(content);
        msgEntity.setType(type);
        msgEntity.setCreateTime(LocalDateTime.now());

        int rows = chatGroupMessageMapper.insert(msgEntity);
        if (rows != 1) throw new BusinessException("群消息写入失败");

        //生成未读记录
        List<ChatGroupMemberEntity> members = chatGroupMapper.selectMembers(groupId);
        for (ChatGroupMemberEntity m : members) {
            if (!m.getUserCode().equals(userId)) {
                chatGroupMessageMapper.insertMessageReadRecord(msgEntity.getId(), m.getUserCode());
            }
        }

        //广播给在线用户
        Map<String, WebSocketSession> onlineUsers = GROUP_ONLINE_USERS.get(groupId);
        if (onlineUsers != null) {
            onlineUsers.forEach((uid, s) -> {
                try {
                    ObjectNode resp = mapper.createObjectNode();
                    resp.put("from", userId);
                    resp.put("msg", content);
                    resp.put("type", type);
                    resp.put("groupId", groupId);

                    s.sendMessage(new TextMessage(resp.toString()));

                    //标记已读
                    chatGroupMessageMapper.updateMessageReadState(msgEntity.getId(), uid);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 遍历群移除下线用户
        GROUP_ONLINE_USERS.values().forEach(map -> map.values().removeIf(s -> s.equals(session)));
        System.out.println("群聊用户下线: " + session + ", status=" + status);
    }
}