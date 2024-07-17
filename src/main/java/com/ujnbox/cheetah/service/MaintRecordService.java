package com.ujnbox.cheetah.service;

import com.ujnbox.cheetah.model.dox.MaintRecordDo;
import com.ujnbox.cheetah.model.vo.MaintRecordVo;

import java.util.List;

public interface MaintRecordService {
    boolean generateNewRecord(String clientName, String phoneNumber, String unit, String room, Integer buildingId, Integer adderId, Integer maintType, String maintDescription, String locationDescription);

    public List<MaintRecordVo> listByStatusAndState(Integer status, Integer state);

    public boolean report(Integer maintRecordId, Integer reporterId, String note, Integer status);
}
