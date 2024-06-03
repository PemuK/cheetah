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

@Service
public class MaintRecordServiceImpl implements MaintRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MaintRecordServiceImpl.class);

    @Autowired
    private MaintRecordDao maintRecordDao;

    @Autowired
    private MaintClientProfileDao maintClientProfileDao;

    @Override
    public boolean generateNewRecord(String clientName, String phoneNumber, String unit, String room, Integer buildingId, Integer adderId, Integer maintType, String maintDescription, String locationDescription) {
        logger.info("Generating new maintenance record for client: {}", clientName);

        // 创建客户档案记录
        MaintClientProfileDo maintClientProfileDo = MaintClientProfileDo.builder()
                .clientName(clientName)
                .phoneNumber(phoneNumber)
                .unit(unit)
                .room(room)
                .buildingId(buildingId)
                .build();
        int clientInsertResult = maintClientProfileDao.insert(maintClientProfileDo);

        // 检查客户档案记录是否插入成功
        if (clientInsertResult == 0) {
            logger.error("Failed to insert client info for client: {}", clientName);
            return false;
        }
        logger.info("Successfully inserted client info for client: {}", clientName);

        // 创建维护记录
        MaintRecordDo maintRecordDo = MaintRecordDo.builder()
                .clientId(maintClientProfileDo.getId())
                .adderId(adderId)
                .maintType(maintType)
                .maintDescription(maintDescription)
                .locationDescription(locationDescription != null && !locationDescription.isEmpty() ? locationDescription : null)
                .build();
        int recordInsertResult = maintRecordDao.insert(maintRecordDo);

        // 检查维护记录是否插入成功
        if (recordInsertResult > 0) {
            logger.info("Successfully inserted maintenance record for client: {}", clientName);
        } else {
            logger.error("Failed to insert maintenance record for client: {}", clientName);
        }

        return recordInsertResult > 0;
    }
}
