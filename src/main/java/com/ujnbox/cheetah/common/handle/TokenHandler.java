package com.ujnbox.cheetah.common.handle;

import com.ujnbox.cheetah.common.model.MsuException;
import com.ujnbox.cheetah.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.ujnbox.cheetah.common.model.ErrorCodeEnum.LOGIN_OVERDUE;

@Component
public class TokenHandler implements HandlerInterceptor {


    @Autowired
    private CookieUtils cookieUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!cookieUtils.isValidCookie(request)) {
            throw new MsuException(LOGIN_OVERDUE);
        }
        return true;  // 正常处理
    }


}
