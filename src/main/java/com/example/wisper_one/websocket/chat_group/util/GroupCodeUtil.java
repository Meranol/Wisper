package com.example.wisper_one.websocket.chat_group.util;

import java.security.SecureRandom;

/**
 * File: GroupCodeUtil
 * Author: [周玉诚]
 * Date: 2026/1/13
 * Description: 随机生成群号工具
 */
public class GroupCodeUtil {
    private GroupCodeUtil() {}
    private static final String CHAR_POOL = "1234567890";
    private static final int MAX_LENGTH = 8;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    public static String generateGroupCode() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < MAX_LENGTH; i++) {
            stringBuilder.append(CHAR_POOL.charAt(SECURE_RANDOM.nextInt(CHAR_POOL.length())));
        }
        return stringBuilder.toString();
    }



}
