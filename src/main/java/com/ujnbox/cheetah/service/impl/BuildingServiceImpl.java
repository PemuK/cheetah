package com.ujnbox.cheetah.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.dao.BuildingDao;
import com.ujnbox.cheetah.model.dox.BuildingDo;
import com.ujnbox.cheetah.model.vo.MaintRecordVo;
import com.ujnbox.cheetah.service.BuildingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.RescaleOp;
import java.util.List;

@Service
@Slf4j
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    BuildingDao buildingDao;

    @Override
    public ResponseMsg<PageInfo> pageByStatus(Integer pageNum, Integer pageSize, Integer status) {
        String orderBy = "id desc"; // 按照排序字段倒序排序
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<BuildingDo> buildingDos = buildingDao.listByStatus(status);
        PageInfo<BuildingDo> pageInfo = new PageInfo<>(buildingDos);
        return ResponseMsg.success(pageInfo);
    }

    @Override
    public ResponseMsg<PageInfo> pageByName(Integer pageNum, Integer pageSize, String name,Integer status) {
        String orderBy = "id desc"; // 按照排序字段倒序排序
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<BuildingDo> buildingDos = buildingDao.listByName(name,status);
        PageInfo<BuildingDo> pageInfo = new PageInfo<>(buildingDos);
        return ResponseMsg.success(pageInfo);
    }

    @Override
    public boolean update(Integer buildingId, String buildingName, Integer type, Integer campus) {
        BuildingDo buildingDo = BuildingDo.builder()
                .id(buildingId)
                .buildingName(buildingName)
                .type(type)
                .campus(campus)
                .build();

        return buildingDao.updateById(buildingDo) > 0;
    }

    @Override
    public boolean updateStatusById(Integer id, Integer status) {
        return buildingDao.updateStatusById(id, status) > 0;
    }

    @Override
    public boolean add(String buildingName, Integer type, Integer campus) {
        BuildingDo buildingDo = BuildingDo.builder()
                .buildingName(buildingName)
                .type(type)
                .campus(campus)
                .build();
        return buildingDao.insert(buildingDo) > 0;
    }

    @Override
    public ResponseMsg<List<BuildingDo>> listByStatus(Integer status) {
        return ResponseMsg.success(buildingDao.listByStatus(status));
    }
}
