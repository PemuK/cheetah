package com.ujnbox.cheetah.common.handle;

import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class CookieCheckHandle implements HandlerInterceptor {
    private static final String LOGIN_PATH = "/account/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath = request.getRequestURI();

        // 如果请求路径不是 /account/login
        if (!requestPath.equals(LOGIN_PATH)) {
            // 检查请求头中是否包含 Cookie
            String cookies = request.getHeader("Cookie");
            if (cookies == null || cookies.isEmpty()) {
                // 如果没有 Cookie 头，返回 400 错误
                response.sendError(ErrorCodeEnum.LOGIN_ERROR.getCode());
                return false; // 阻止请求进一步处理
            }
        }

        // 允许请求继续处理
        return true;
    }
}
