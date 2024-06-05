package com.ujnbox.cheetah.service.impl;

import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.dao.MaintClientProfileDao;
import com.ujnbox.cheetah.dao.MaintRecordDao;
import com.ujnbox.cheetah.model.dox.MaintClientProfileDo;
import com.ujnbox.cheetah.model.dox.MaintRecordDo;
import com.ujnbox.cheetah.service.MaintRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 为客户生成新的维护记录。
     *
     * @param clientName          客户姓名。
     * @param phoneNumber         客户电话号码。
     * @param unit                客户所在单元。
     * @param room                客户所在房间。
     * @param buildingId          客户所在建筑的ID。
     * @param adderId             添加维护记录的人员ID。
     * @param maintType           维护类型。
     * @param maintDescription    维护描述。
     * @param locationDescription 维护地点描述。
     * @return 如果维护记录生成成功则返回 true，否则返回 false。
     */
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
        logger.error("客户建档: {}, {}, {}, {}, {}", clientName, phoneNumber, unit, room, buildingId);

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
}
