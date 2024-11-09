package com.ujnbox.cheetah.controller;

import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseMsg<?> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        return accountService.login(username, password, response);
    }

    @PostMapping("/logout")
    public ResponseMsg<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String userId = getCookieValue(request, "id");
        if (userId == null) {
            return ResponseMsg.error(ErrorCodeEnum.OUT_ERROR);
        }
        return accountService.outLogin(response, userId);
    }

    @PostMapping("/change-password")
    public ResponseMsg<?> changePassword(
            @RequestParam Integer userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            HttpServletRequest request) {
        // 检查用户是否已登录
        String cookieUserId = getCookieValue(request, "id");
        if (cookieUserId == null || !cookieUserId.equals(userId.toString())) {
            return ResponseMsg.error(ErrorCodeEnum.OUT_ERROR); // 用户 ID 不匹配或用户未登录
        }
        return accountService.changePassword(userId, oldPassword, newPassword);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
