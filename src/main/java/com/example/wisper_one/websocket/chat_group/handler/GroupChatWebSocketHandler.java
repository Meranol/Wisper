package com.example.wisper_one.websocket.chat_group.handler;

import com.example.wisper_one.Login.mapper.UserMapper;
import com.example.wisper_one.utils.Exception.BusinessException;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMemberEntity;
import com.example.wisper_one.websocket.chat_group.POJO.ChatGroupMessageEntity;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMapper;
import com.example.wisper_one.websocket.chat_group.mapper.ChatGroupMessageMapper;
import com.example.wisper_one.websocket.util.GlobalWsSessionManager;
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
    private static final Map<String, Map<String, WebSocketSession>> GROUP_ONLINE_USERS = new ConcurrentHashMap<>();

    @Resource
    private ChatGroupMapper chatGroupMapper;

    @Resource
    private ChatGroupMessageMapper chatGroupMessageMapper;
    @Resource
    private UserMapper userMapper;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        String groupcode = null;
        String query = session.getUri().getQuery();

        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                if (param.startsWith("groupId=")) {
                    groupcode = param.substring("groupId=".length());
                }
            }
        }


        System.out.println("群聊连接🔗"+userId+"群号"+groupcode);

        if (userId == null || groupcode == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("userId or groupId missing"));
            return;
        }

        //验证是否为群成员，不是拒绝连接
        String userCode = userMapper.selectCodeByUname(userId);
        ChatGroupMemberEntity member = chatGroupMapper.selectMember(groupcode, userCode);
        if (member == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("你不在这个群里"));
            return;
        }


        session.getAttributes().put("groupId", groupcode);
        // 保存在线状态
        GROUP_ONLINE_USERS.putIfAbsent(groupcode, new ConcurrentHashMap<>());
        GROUP_ONLINE_USERS.get(groupcode).put(userCode, session);

        // 获取离线未读消息
        List<ChatGroupMessageEntity> unreadMessages = chatGroupMessageMapper.selectUnreadMessages(groupcode, userCode);

        for (ChatGroupMessageEntity msg : unreadMessages) {
            ObjectNode node = mapper.createObjectNode();
            node.put("from", msg.getSenderId());
            node.put("msg", msg.getContent());
            node.put("type", msg.getType());
            node.put("groupId", msg.getGroupCode());

            session.sendMessage(new TextMessage(node.toString()));
            System.out.println("已读数据库修改“："+msg.getId()+":"+userCode);

            //已读消息兜底插入
            chatGroupMessageMapper.insertMessageReadRecord(
                    msg.getId(), userCode
            );

            int result = chatGroupMessageMapper.updateMessageReadState(
                    msg.getId(), userCode
            );

            System.out.println("已读影响行数：" + result);

        }


        GlobalWsSessionManager.add(userCode, session);

        System.out.println("用户上线群聊: " + userId + ", groupId=" + groupcode);
        System.out.println(GROUP_ONLINE_USERS);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        String groupcode = (String) session.getAttributes().get("groupId");
        System.out.println("handleTextMessage"+"已连接，群号："+ groupcode);


        ObjectNode json = (ObjectNode) mapper.readTree(message.getPayload());
        String content = json.get("msg").asText();
        String type = json.has("type") ? json.get("type").asText() : "text";

        if (content.isEmpty() || content.length() > 2000) {
            session.sendMessage(new TextMessage("消息内容不合法"));
            return;
        }


        String usercode = userMapper.selectCodeByUname(userId);
        System.out.println("handleTextMessage"+"已连接，用户账号："+usercode);

        ChatGroupMemberEntity member = chatGroupMapper.selectMember(groupcode, usercode);
        if (member == null) {
            session.sendMessage(new TextMessage("你不在这个群里"));
            return;
        }


        ChatGroupMessageEntity msgEntity = new ChatGroupMessageEntity();
        msgEntity.setGroupCode(groupcode);
        msgEntity.setSenderId(userId);
        msgEntity.setContent(content);
        msgEntity.setType(type);
        msgEntity.setCreateTime(LocalDateTime.now());

        int rows = chatGroupMessageMapper.insert(msgEntity);
        if (rows != 1) throw new BusinessException("群消息写入失败");

        //生成未读

        List<ChatGroupMemberEntity> members = chatGroupMapper.selectMembers(groupcode);

        for (ChatGroupMemberEntity m : members) {
            System.out.println("生成未读区域"+m);
            if (!m.getUserCode().equals(userId)) {
                chatGroupMessageMapper.insertMessageReadRecord(msgEntity.getId(), m.getUserCode());
            }
        }

//        List<ChatGroupMemberEntity> members = chatGroupMapper.selectMembers(groupcode);
//        for (ChatGroupMemberEntity m : members) {
//            String mUserCode = m.getUserCode();
//            if (mUserCode != null && !Objects.equals(mUserCode, userId)) {
//                chatGroupMessageMapper.insertMessageReadRecord(msgEntity.getId(), mUserCode);
//            }
//        }

        //广播给送给在线🐔友
        Map<String, WebSocketSession> onlineUsers = GROUP_ONLINE_USERS.get(groupcode);
        if (onlineUsers != null) {
            for (Map.Entry<String, WebSocketSession> entry : onlineUsers.entrySet()) {
                String uid = entry.getKey();
                WebSocketSession s = entry.getValue();



                ObjectNode resp = mapper.createObjectNode();
                resp.put("from", userId);
                resp.put("msg", content);
                resp.put("type", type);
                resp.put("groupId", groupcode);

                s.sendMessage(new TextMessage(resp.toString()));
                String ucode = userMapper.selectCodeByUname(uid);
                chatGroupMessageMapper.insertMessageReadRecord(msgEntity.getId(), ucode);
                chatGroupMessageMapper.updateMessageReadState(
                        msgEntity.getId(), ucode
                );
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 遍历群移除下线用户
        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            String userCode = userMapper.selectCodeByUname(userId);
            GlobalWsSessionManager.remove(userCode, session);
        }

        GROUP_ONLINE_USERS.values().forEach(map -> map.values().removeIf(s -> s.equals(session)));
        System.out.println("群聊用户下线: " + session + ", status=" + status);
    }




}