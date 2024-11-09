package com.ujnbox.cheetah.service;

import com.github.pagehelper.PageInfo;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.vo.MaintRecordVo;
import com.ujnbox.cheetah.model.vo.WorkAmountVo;

import java.time.LocalDateTime;
import java.util.List;

public interface MaintRecordService {

    boolean generateNewRecord(Integer clientId, String clientName, String phoneNumber, String unit, String room, Integer buildingId, Integer adderId, String maintDescription, String locationDescription);

    PageInfo<MaintRecordVo> getFinishedMaints(Integer pageNum, Integer pageSize, Integer state);

    List<MaintRecordVo> getByIdAndState(Integer id, Integer state);

    List<MaintRecordVo> listByClientName(String clientName, Integer state);

    List<MaintRecordVo> listByPhoneNumber(String phoneNumber, Integer state);

    List<MaintRecordVo> listByLocation(Integer buildingId, String unit, String room, Integer state);

    List<MaintRecordVo> getUnfinishedMaints(Integer state);

    boolean report(Integer maintRecordId, Integer reporterId, String players, Integer maintType, String note, Integer status);

    boolean updateStatus(Integer maintRecordId, Integer status);

    boolean updateState(Integer maintRecordId, Integer state);

    ResponseMsg updateMaint(Integer userId, Integer maintRecordId, Integer clientId, String clientName, String phoneNumber, Integer buildingId, String unit, String room, String maintDescription, String locationDescription, String players, Integer maintType, String note, Integer status);

    PageInfo listByBuildingUnitRoom(Integer pageNum, Integer pageSize, String searchTerm, Integer state);

    PageInfo pageByClientName(Integer pageNum, Integer pageSize, String clientName, Integer state);

    PageInfo pageByPhoneNumber(Integer pageNum, Integer pageSize, String phoneNumber, Integer state);

    PageInfo pageByMaintType(Integer pageNum, Integer pageSize, Integer typeId, Integer state);

    PageInfo pageByDescription(Integer pageNum, Integer pageSize, String description, Integer state);

    List<MaintRecordVo> listByIdAndMonth(LocalDateTime time, Integer userId, Integer state);

    List<MaintRecordVo> listByIdAndTime(LocalDateTime startTime, LocalDateTime endTime, Integer userId, Integer state);
}
