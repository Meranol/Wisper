package com.example.wisper_one.utils.jwt;

import com.example.wisper_one.Login.common.Result;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
/**
 * File: JwtTokenUtil
 * Author: [周玉诚]
 * Date: 2026/1/10
 * Description:
 */
public class JwtTokenUtil {
    private static final String SECRET_KEY = "12345678901234567890123456789012";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // 生成 token
    public static String generateToken(String username, long expireMillis) {
        Date now = new Date();
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireMillis))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();

        // 调试：打印生成的token
        System.out.println("✅ 生成的Token: " + token);
        System.out.println("🔑 使用的密钥: " + SECRET_KEY);
        System.out.println("👤 用户名: " + username);
        System.out.println("⏰ 过期时间: " + new Date(now.getTime() + expireMillis));

        return token;
    }

    // 验证 token
    public static Result<String> verifyToken(String token) {
        System.out.println("🔍 开始验证Token: " + token);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("✅ Token验证成功");
            System.out.println("👤 用户名: " + claims.getSubject());
            System.out.println("📅 签发时间: " + claims.getIssuedAt());
            System.out.println("📅 过期时间: " + claims.getExpiration());

            return Result.success(claims.getSubject());
        } catch (ExpiredJwtException e) {
            System.err.println("❌ Token已过期: " + e.getMessage());
            return Result.failure("Token 已过期");
        } catch (MalformedJwtException e) {
            System.err.println("❌ Token格式错误: " + e.getMessage());
            return Result.failure("Token 格式错误");
        } catch (SignatureException e) {
            System.err.println("❌ Token签名无效: " + e.getMessage());
            System.err.println("⚠️  可能原因：密钥不匹配或Token被篡改");
            return Result.failure("Token 签名无效");
        } catch (JwtException e) {
            System.err.println("❌ Token验证失败: " + e.getMessage());
            e.printStackTrace();
            return Result.failure("Token 无效");
        }
    }
}