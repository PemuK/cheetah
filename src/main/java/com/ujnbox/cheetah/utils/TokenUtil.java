package com.ujnbox.cheetah.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Component
public class TokenUtil {

    // 生成一个简单的Token
    public static String generateToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    // 生成一个Base64编码的Token
    public static String generateBase64Token() {
        UUID uuid = UUID.randomUUID();
        return Base64.getEncoder().encodeToString(uuid.toString().getBytes());
    }

    // 验证Token是否符合UUID格式
    public static boolean validateToken(String token) {
        try {
            UUID uuid = UUID.fromString(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
