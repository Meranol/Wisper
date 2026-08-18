# Wisper_one WebSocket 接口说明

> 说明：WebSocket 接口无法用 OpenAPI 表达，本文件补充说明。HTTP 接口见 `Wisper_one_API.json`（可直接导入 Apifox）。

## 通用信息

- 地址前缀：`ws://localhost:8089`
- 握手鉴权：URL 后拼接 `?token=<登录返回的 token>`，或请求头 `Authorization: Bearer <token>`
- 握手校验逻辑：token 必须有效，且 Redis 中的 `login:token:<usercode>` 仍存在且相等（否则视为被顶号/被踢）

---

## 1. 单聊 `/ws/chat`

| 项 | 值 |
|---|---|
| 连接地址 | `ws://localhost:8089/ws/chat?token=<token>` |
| 说明 | 建立连接后自动推送未读消息 |

**发送消息（客户端 → 服务端）：**

```json
{ "to": "<对方 userCode>", "msg": "你好", "type": "text" }
```

- `to`：接收方 userCode（必填）
- `msg`：消息内容（必填，1~2000 字符）
- `type`：消息类型，默认 `text`，可省略

**接收消息（服务端 → 客户端）：**

```json
{ "from": "<发送者 userCode>", "msg": "你好", "type": "text" }
```

错误提示会直接以纯文本返回，例如：`消息格式错误`、`消息内容不合法`、`发送失败：该用户不存在`。

---

## 2. 群聊 `/ws/group`

| 项 | 值 |
|---|---|
| 连接地址 | `ws://localhost:8089/ws/group?token=<token>&groupId=<群号>` |
| 说明 | 必须携带 `groupId`；非群成员会被拒绝连接 |

**发送消息（客户端 → 服务端）：**

```json
{ "msg": "大家好", "type": "text" }
```

- `msg`：消息内容（必填，1~2000 字符）
- `type`：消息类型，默认 `text`，可省略

**接收消息（服务端 → 客户端）：**

```json
{ "from": "<用户名>", "msg": "大家好", "type": "text", "groupId": "<群号>" }
```

> 注意：群聊消息里 `from` 为发送者的**用户名**（username），单聊里 `from` 为发送者的 **userCode**。

---

## 3. 在线状态 `/ws/online`

| 项 | 值 |
|---|---|
| 连接地址 | `ws://localhost:8089/ws/online?token=<token>` |
| 说明 | 连接后广播当前用户上线；断开（或被踢）广播下线 |

**接收消息（服务端 → 客户端）：**

```json
{ "event": "ONLINE", "userCode": "<某用户 userCode>" }
{ "event": "OFFLINE", "userCode": "<某用户 userCode>" }
```

- 建立连接时会收到当前所有在线用户的 `ONLINE` 事件
- 任意用户上/下线时，会向所有在线连接广播对应 `event`

---

## 4. 鉴权参数细节

- 单聊/群聊握手拦截器 `WsAuthInterceptor` 要求查询串以 `token=` 开头，`token` 请放在**最前面**。
- 群聊 `groupId` 通过查询参数 `groupId` 传递，需放在 `token` 之后：`?token=xxx&groupId=yyy`。
