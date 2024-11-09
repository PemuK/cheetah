package com.ujnbox.cheetah.service;

import com.github.pagehelper.PageInfo;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.dox.BuildingDo;

import java.util.List;

public interface BuildingService {
    ResponseMsg<PageInfo> pageByStatus(Integer pageNum, Integer pageSize, Integer status);

    ResponseMsg<PageInfo> pageByName(Integer pageNum, Integer pageSize, String name,Integer status);


    boolean update(Integer buildingId, String buildingName, Integer type, Integer campus);

    boolean updateStatusById(Integer id, Integer status);

    boolean add(String buildingName, Integer type, Integer campus);

    ResponseMsg<List<BuildingDo>> listByStatus(Integer status);


}
