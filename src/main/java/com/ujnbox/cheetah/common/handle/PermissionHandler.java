package com.ujnbox.cheetah.common.handle;

import com.ujnbox.cheetah.common.annotation.RequiresPermission;
import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.MsuException;
import com.ujnbox.cheetah.dao.UserDao;
import com.ujnbox.cheetah.model.dox.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Optional;

@Component
public class PermissionHandler implements HandlerInterceptor {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 确保该拦截器仅拦截 Controller 方法
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            // 检查是否标记了 @RequiresPermission 注解
            RequiresPermission requiresPermission = method.getAnnotation(RequiresPermission.class);
            if (requiresPermission != null) {
                // 从请求中获取 cookie 中的 id
                String userId = null;
                if (request.getCookies() != null) {
                    for (Cookie cookie : request.getCookies()) {
                        if ("id".equals(cookie.getName())) {
                            userId = cookie.getValue();
                            break;
                        }
                    }
                }

                if (userId == null) {
                    throw new MsuException(ErrorCodeEnum.LOGIN_OVERDUE);
                }

                UserDo user = userDao.getById(Integer.parseInt(userId));
                if (user == null) {
                    throw new MsuException(ErrorCodeEnum.NULL_ERROR);

                }

                // 检查用户的 status 是否满足要求
                if (user.getStatus() < requiresPermission.value()) {
                    throw new MsuException(ErrorCodeEnum.NOT_PERMISSION);
                }

            }
        }
        return true;
    }
}
