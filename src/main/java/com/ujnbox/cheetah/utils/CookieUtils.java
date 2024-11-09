package com.ujnbox.cheetah.utils;

import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.MsuException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieUtils {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public CookieUtils(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new MsuException(ErrorCodeEnum.NOT_LOGIN);
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public boolean isValidCookie(HttpServletRequest request) {
        String token = getCookieValue(request, "token");
        String userId = getCookieValue(request, "id");

        if (token == null || userId == null) {
            throw new MsuException(ErrorCodeEnum.NOT_LOGIN);
        }

        // Check token validity in Redis
        String storedToken = redisTemplate.opsForValue().get(userId);
        if (storedToken != null && storedToken.equals(token)) {
            return true;
        } else {
            return false;
        }
    }
}
