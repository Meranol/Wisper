package com.example.wisper_one.websocket.online;

import com.example.wisper_one.Login.common.Result;
import com.example.wisper_one.utils.ResultCode;
import com.example.wisper_one.utils.jwt.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * OnlineWsInterceptor: WebSocket 握手拦截器，支持 Redis 校验 token 是否有效
 */
@Component
public class OnlineWsInterceptor implements HandshakeInterceptor {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        String token = null;

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest servletReq = servletRequest.getServletRequest();

            String query = servletReq.getQueryString();
            if (query != null && query.startsWith("token=")) {
                token = query.substring(6);
            }

            if (token == null) {
                String auth = servletReq.getHeader("Authorization");
                if (auth != null && auth.startsWith("Bearer ")) {
                    token = auth.substring(7);
                }
            }
        }

        if (token == null) return false;

        Result<String> result = JwtTokenUtil.verifyToken(token);
        if (result.getCode() != ResultCode.SUCCESS) return false;

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor("12345678901234567890123456789012".getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userCode = claims.get("usercode", String.class);

        // 4. Redis 校验，是否被踢掉
        String redisKey = "login:token:" + userCode;
        String redisToken = redisTemplate.opsForValue().get(redisKey);

        if (redisToken == null || !redisToken.equals(token)) {
            return false; // token 无效，被踢掉或已失效
        }

        attributes.put("userCode", userCode);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
    }
}
