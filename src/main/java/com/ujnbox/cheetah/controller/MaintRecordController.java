package com.ujnbox.cheetah.controller;

import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.service.MaintRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("maint-records")
public class MaintRecordController {
    @Autowired
    private MaintRecordService maintRecordService;

    @PostMapping("/add")
    public ResponseMsg generateNewRecord(@RequestParam(value = "clientName") String clientName,
                                         @RequestParam(value = "phoneNumber") String phoneNumber,
                                         @RequestParam(value = "unit", required = false, defaultValue = "") String unit,
                                         @RequestParam(value = "room") String room,
                                         @RequestParam(value = "buildingId") Integer buildingId,
                                         @RequestParam(value = "adderId") Integer adderId,
                                         @RequestParam(value = "maintType") Integer maintType,
                                         @RequestParam(value = "maintDescription") String maintDescription,
                                         @RequestParam(value = "locationDescription") String locationDescription) {

        boolean success = maintRecordService.generateNewRecord(clientName, phoneNumber, unit, room, buildingId, adderId, maintType, maintDescription, locationDescription);

        // 根据服务方法的返回值返回对应的响应消息
        if (success) {
            return ResponseMsg.success();
        } else {
            return ResponseMsg.error(600, "Failed to add record");
        }
    }
}
