package com.ujnbox.cheetah.service.impl;

import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
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
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public ResponseMsg<?> login(String username, String password, HttpServletResponse response) {
        log.info("[login] username: {}, password: {}", username, password);

        UserDo userDo = userDao.login(username, password);
        UserVo userVo;
        if (userDo!=null){
            userVo=UserVo.builder()
                    .id(userDo.getId())
                    .username(userDo.getUsername())
                    .name(userDo.getName())
                    .gender(userDo.getGender())
                    .age(userDo.getAge())
                    .status(userDo.getStatus())
                    .startYear(userDo.getStartYear())
                    .updateTime(userDo.getUpdateTime())
                    .createTime(userDo.getCreateTime())
                    .phoneNumber(userDo.getPhoneNumber())
                    .organizationId(userDo.getOrganizationId())
                    .organization(organizationDao.getByIdAndStatus(userDo.getOrganizationId(),1).getOrganizationName())
                    .build();
            // 生成Token
            String token = TokenUtil.generateToken();

            // 创建存储Token的Cookie
            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setMaxAge(6 * 60 * 60); // 过期时间为6小时
            tokenCookie.setPath("/");

            // 创建存储用户ID的Cookie
            Cookie userIdCookie = new Cookie("id", userDo.getId().toString());
            userIdCookie.setMaxAge(6 * 60 * 60);
            userIdCookie.setPath("/");

            // 将Cookie添加到响应中
            response.addCookie(tokenCookie);
            response.addCookie(userIdCookie);
            return ResponseMsg.success(userVo);
        }else{
            return ResponseMsg.error(ErrorCodeEnum.LOGIN_ERROR);
        }
    }
}
