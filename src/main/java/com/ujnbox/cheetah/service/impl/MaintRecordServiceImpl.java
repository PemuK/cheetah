package com.ujnbox.cheetah.service.impl;

import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.dao.BuildingDao;
import com.ujnbox.cheetah.dao.MaintClientProfileDao;
import com.ujnbox.cheetah.dao.MaintRecordDao;
import com.ujnbox.cheetah.dao.UserDao;
import com.ujnbox.cheetah.model.dox.BuildingDo;
import com.ujnbox.cheetah.model.dox.MaintClientProfileDo;
import com.ujnbox.cheetah.model.dox.MaintRecordDo;
import com.ujnbox.cheetah.model.dox.UserDo;
import com.ujnbox.cheetah.model.vo.MaintRecordVo;
import com.ujnbox.cheetah.service.MaintRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * MaintRecordService 接口的实现类。
 * 提供生成维护记录的服务。
 */
@Service
public class MaintRecordServiceImpl implements MaintRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MaintRecordServiceImpl.class);

    @Autowired
    private MaintRecordDao maintRecordDao;

    @Autowired
    private MaintClientProfileDao maintClientProfileDao;

    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private UserDao userDao;

    @Override
    public boolean generateNewRecord(String clientName, String phoneNumber, String unit, String room, Integer buildingId, Integer adderId, Integer maintType, String maintDescription, String locationDescription) {
        logger.info("正在为客户生成新的维护记录: {}", clientName);

        // 创建客户档案记录
        MaintClientProfileDo maintClientProfileDo = MaintClientProfileDo.builder()
                .clientName(clientName)
                .phoneNumber(phoneNumber)
                .unit(unit)
                .room(room)
                .buildingId(buildingId)
                .build();

        // 将客户档案记录插入数据库
        maintClientProfileDao.insert(maintClientProfileDo);

        // 记录客户档案创建结果
        logger.info("客户建档: {}, {}, {}, {}, {}", clientName, phoneNumber, unit, room, buildingId);

        // 创建维护记录
        MaintRecordDo maintRecordDo = MaintRecordDo.builder()
                .clientId(maintClientProfileDo.getId())
                .adderId(adderId)
                .maintType(maintType)
                .maintDescription(maintDescription)
                .locationDescription(locationDescription != null && !locationDescription.isEmpty() ? locationDescription : null)
                .build();

        // 将维护记录插入数据库
        int recordInsertResult = maintRecordDao.insert(maintRecordDo);

        // 记录维护记录创建结果
        logger.info("维护记录建立: {}, {}, {}, {}, {}", maintClientProfileDo.getId(), adderId, maintType, maintDescription, locationDescription);

        // 返回维护记录插入操作的结果
        return recordInsertResult > 0;
    }

    @Override
    public List<MaintRecordVo> listByStatusAndState(Integer status, Integer state) {
        List<MaintRecordVo> maintRecordVos = new ArrayList<>();
        logger.info("查询维护记录，完成情况: {} ，状态: {}", status, state);

        // 从数据库获取维护记录列表
        List<MaintRecordDo> maintRecords = maintRecordDao.listByStatusAndState(status, state);

        for (MaintRecordDo maintRecordDo : maintRecords) {
            // 获取客户档案
            MaintClientProfileDo clientProfile = maintClientProfileDao.getByIdAndStatus(maintRecordDo.getClientId(), 1);
            if (clientProfile != null) {
                // 获取建筑信息
                BuildingDo building = buildingDao.getByIdAndStatus(clientProfile.getBuildingId(), 1);
                // 获取添加者和报告者信息
                UserDo adderUser = userDao.getByIdAndStatus(maintRecordDo.getAdderId(), 1);
                UserDo reportUser = userDao.getByIdAndStatus(maintRecordDo.getReportId(), 1);

                // 构建维护记录视图对象
                MaintRecordVo maintRecordVo = MaintRecordVo.builder()
                        .id(maintRecordDo.getId())
                        .clientName(clientProfile.getClientName())
                        .phoneNumber(clientProfile.getPhoneNumber())
                        .unit(clientProfile.getUnit())
                        .room(clientProfile.getRoom())
                        .buildingName(building != null ? building.getBuildingName() : null)
                        .adderName(adderUser != null ? adderUser.getName() : null)
                        .reportName(reportUser != null ? reportUser.getName() : null)
                        .maintType(maintRecordDo.getMaintType())
                        .maintDescription(maintRecordDo.getMaintDescription())
                        .locationDescription(maintRecordDo.getLocationDescription())
                        .createTime(maintRecordDo.getCreateTime())
                        .updateTime(maintRecordDo.getUpdateTime())
                        .build();

                maintRecordVos.add(maintRecordVo);
            }
        }
        return maintRecordVos;
    }


    /**
     * 提交填报信息并更新数据库
     *
     * @param maintRecordId 维护记录ID
     * @param reporterId    填报人ID
     * @param note          备注信息
     * @param status        状态信息
     * @return 如果更新成功返回true，否则返回false
     */
    @Override
    public boolean report(Integer maintRecordId, Integer reporterId, String note, Integer status) {


        // 记录填报备注信息
        logger.info("填报维护id：{}，填报人id：{}，备注：{}，更改完成状态：{}", maintRecordId, reporterId, note, status);

        // 更新填报备注
        boolean noteUpdateSuccess = updateNote(maintRecordId, note);

        // 更新填报人信息
        boolean reporterIdUpdateSuccess = updateReporterId(maintRecordId, reporterId);

        // 更新填报状态信息
        boolean statusUpdateSuccess = updateStatus(maintRecordId, status);

        // 返回更新结果
        return noteUpdateSuccess && statusUpdateSuccess && reporterIdUpdateSuccess;
    }

    /**
     * 更新维护记录状态
     *
     * @param maintRecordId 维护记录ID
     * @param status        状态信息
     * @return 如果更新成功返回true，否则返回false
     */
    private boolean updateStatus(Integer maintRecordId, Integer status) {
        return maintRecordDao.updateStatusById(status, maintRecordId) > 0;
    }

    /**
     * 更新填报人信息
     *
     * @param maintRecordId 维护记录ID
     * @param reporterId    填报人ID
     * @return 如果更新成功返回true，否则返回false
     */
    private boolean updateReporterId(Integer maintRecordId, Integer reporterId) {
        return maintRecordDao.updateReporterIdById(reporterId, maintRecordId) > 0;
    }

    /**
     * 更新填报备注信息
     *
     * @param maintRecordId 维护记录ID
     * @param note          备注信息
     * @return 如果更新成功返回true，否则返回false
     */
    private boolean updateNote(Integer maintRecordId, String note) {
        return maintRecordDao.updateNoteById(note, maintRecordId) > 0;
    }
}
