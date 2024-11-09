package com.ujnbox.cheetah.service.impl;

import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.PermissionEnum;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.dao.OrganizationDao;
import com.ujnbox.cheetah.dao.UserDao;
import com.ujnbox.cheetah.model.dox.UserDo;
import com.ujnbox.cheetah.model.vo.UserVo;
import com.ujnbox.cheetah.service.AccountService;
import com.ujnbox.cheetah.utils.TokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.ujnbox.cheetah.common.model.ErrorCodeEnum.OUT_ERROR;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ResponseMsg<?> login(String username, String password, HttpServletResponse response) {
        log.info("[login] username: {}, password: {}", username, password);

        UserDo userDo = userDao.login(username, password);
        UserVo userVo;
        if (userDo != null) {
            // 生成 Token
            String token = TokenUtil.generateToken();

            redisTemplate.opsForValue().set(userDo.getId().toString(), token, 1, TimeUnit.DAYS);

            // 手动添加 SameSite=None 属性
            String tokenCookie = String.format("token=%s; Path=/; HttpOnly; Secure; SameSite=None", token);
            String userIdCookie = String.format("id=%s; Path=/; HttpOnly; Secure; SameSite=None", userDo.getId().toString());

            response.addHeader("Set-Cookie", tokenCookie);
            response.addHeader("Set-Cookie", userIdCookie);

            userVo = UserVo.builder()
                    .id(userDo.getId())
                    .username(userDo.getUsername())
                    .name(userDo.getName())
                    .startYear(userDo.getStartYear())
                    .status(userDo.getStatus())
                    .permission(PermissionEnum.fromCode(userDo.getStatus()).getDescription())
                    .startYear(userDo.getStartYear())
                    .updateTime(userDo.getUpdateTime())
                    .createTime(userDo.getCreateTime())
                    .phoneNumber(userDo.getPhoneNumber())
                    .organizationId(userDo.getOrganizationId())
                    .token(token)
                    .organizationName(organizationDao.getByIdAndStatus(userDo.getOrganizationId(), 1).getOrganizationName())
                    .build();

            return ResponseMsg.success(userVo);
        } else {
            return ResponseMsg.error(ErrorCodeEnum.LOGIN_ERROR);
        }
    }

    @Override
    public ResponseMsg<?> outLogin(HttpServletResponse response, String userId) {
        try {
            // 从 Redis 中删除用户的 Token
            redisTemplate.delete(userId);

            // 清除客户端的 Cookie
            Cookie tokenCookie = new Cookie("token", null);
            tokenCookie.setPath("/");

            Cookie userIdCookie = new Cookie("id", null);
            userIdCookie.setPath("/");

            // 将删除的 Cookie 添加到响应中
            response.addCookie(tokenCookie);
            response.addCookie(userIdCookie);

            log.info("[outLogin] User with ID {} has logged out successfully.", userId);

            return ResponseMsg.success("登出成功");
        } catch (Exception e) {
            log.error("[outLogin] Error during logout: ", e);
            return ResponseMsg.error(OUT_ERROR);
        }
    }

    @Override
    public ResponseMsg<?> changePassword(Integer userId, String oldPassword, String newPassword) {
        // 查找用户信息
        UserDo user = userDao.getById(userId);

        if (user == null) {
            return ResponseMsg.error(ErrorCodeEnum.USER_NOT_EXISTED);
        }

        // 校验旧密码是否正确
        if (!user.getPassword().equals(oldPassword)) {
            return ResponseMsg.error(ErrorCodeEnum.OLD_PASSWORD_ERROR);
        }

        // 更新密码
        int rowsAffected = userDao.updatePasswordById(oldPassword, newPassword, userId);

        if (rowsAffected > 0) {
            return ResponseMsg.success();
        } else {
            return ResponseMsg.error(ErrorCodeEnum.UPDATE_ERROR);
        }
    }
}
