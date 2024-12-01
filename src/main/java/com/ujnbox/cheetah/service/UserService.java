package com.ujnbox.cheetah.service;

import com.github.pagehelper.PageInfo;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.vo.UserVo;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseMsg<?> addUser(String username, String name, String password, String phoneNumber, Integer startYear, Integer organizationId, Integer status);

    ResponseMsg<List<UserVo>> listByStatusAndState(Integer status, Integer state);

    boolean updateById(Integer id, String name, String username, Integer organizationId, String password, Integer level, String phoneNumber, Integer status);

    PageInfo<UserVo> pageByState(Integer pageNum, Integer pageSize, Integer state);

    List<UserVo> listByState(Integer state);

    PageInfo listByNameAndState(Integer pageNum, Integer pageSize,String name, Integer state);

    List<String> getAllStartYears(Integer state);

    Map<String, List<UserVo>> listByStartYear(Integer status, Integer state);

    boolean updateStateById(Integer id, Integer state);

    PageInfo<UserVo> pageOrdinary(Integer pageNum, Integer pageSize, Integer state,Integer status);
}
