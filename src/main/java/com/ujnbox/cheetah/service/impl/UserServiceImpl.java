package com.ujnbox.cheetah.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.MsuException;
import com.ujnbox.cheetah.common.model.PermissionEnum;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.dao.UserDao;
import com.ujnbox.cheetah.model.dox.UserDo;
import com.ujnbox.cheetah.model.vo.UserVo;
import com.ujnbox.cheetah.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public ResponseMsg<?> addUser(String username, String name, String password, String phoneNumber, Integer startYear, Integer organizationId,Integer status) {
        UserDo userDo = UserDo.builder()
                .username(username)
                .name(name)
                .password(password)
                .phoneNumber(phoneNumber)
                .startYear(startYear)
                .organizationId(organizationId)
                .build();

        UserDo byUsernameAndState = userDao.getByUsernameAndState(username, 1);
        if (byUsernameAndState != null) {
            return ResponseMsg.error(ErrorCodeEnum.USERNAME_IS_EXISTED);
        }

        return ResponseMsg.success(userDao.insert(userDo));
    }

    @Override
    public ResponseMsg<List<UserVo>> listByStatusAndState(Integer status, Integer state) {
        log.info("[listByStatus] 正在获取状态为 {} 的用户列表", status);

        // 根据状态从数据库中获取用户列表
        List<UserVo> userVos = userDao.listByStatusAndState(status,state);

        return ResponseMsg.success(userVos);
    }

    @Override
    public boolean updateById(Integer id, String name, String username, Integer organizationId, String password, Integer level, String phoneNumber, Integer status) {
        UserDo userDo = UserDo.builder()
                .id(id)
                .name(name)
                .username(username)
                .organizationId(organizationId)
                .password(password)
                .startYear(level)
                .phoneNumber(phoneNumber)
                .status(status)
                .build();

        UserDo byUsernameAndState = userDao.getByUsernameAndState(username, 1);

        if (byUsernameAndState != null && !byUsernameAndState.getId().equals(id)) {
            throw new MsuException(ErrorCodeEnum.USERNAME_IS_EXISTED);
        }


        return userDao.updateById(userDo) > 0;
    }

    @Override
    public PageInfo<UserVo> pageByState(Integer pageNum, Integer pageSize, Integer state) {
        log.info("[list] 正在获取所有用户列表");
        // 启动分页
        String orderBy = "id desc"; // 按照排序字段倒序排序
        PageHelper.startPage(pageNum, pageSize, orderBy);
        // 从数据库中获取所有用户
        List<UserVo> userVos = userDao.listByState(state);

        // 遍历用户列表，并为每个用户设置权限
        for (UserVo userVo : userVos) {
            int status = userVo.getStatus(); // 假设 status 对应权限
            String permission = PermissionEnum.fromCode(status).getDescription();
            userVo.setPermission(permission);
        }

        PageInfo<UserVo> pageInfo = new PageInfo<>(userVos);
        return pageInfo;
    }

    @Override
    public List<UserVo> listByState(Integer state) {
        return userDao.listByState(state);
    }

    @Override
    public PageInfo listByNameAndState(Integer pageNum, Integer pageSize,String name, Integer state) {
        // 启动分页
        String orderBy = "id desc"; // 按照排序字段倒序排序
        PageHelper.startPage(pageNum, pageSize, orderBy);
        // 从数据库中获取所有用户
        List<UserVo> userVos = userDao.listByName(name,state);
        PageInfo<UserVo> pageInfo = new PageInfo<>(userVos);

        return pageInfo;
    }

    @Override
    public List<String> getAllStartYears(Integer state) {
        List<String> startYears = userDao.listAllStartYears(state);

        // Remove null or empty values
        return startYears.stream()
                .filter(startYear -> startYear != null && !startYear.trim().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<UserVo>> listByStartYear(Integer status,Integer state) {
        Map<String, List<UserVo>> userYearMap = new HashMap<>();
        List<String> years = userDao.listAllStartYears(state);
        for (String year : years) {
            if (year != null && !year.trim().isEmpty()) {
                userYearMap.put(year, userDao.listByStartYear(Integer.parseInt(year), status));
            }
        }
        return userYearMap;
    }

    @Override
    public boolean updateStateById(Integer id, Integer state) {
        return userDao.updateStateById(state, id) > 0;
    }
}
