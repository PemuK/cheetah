package com.ujnbox.cheetah.controller;

import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.vo.MaintRecordVo;
import com.ujnbox.cheetah.service.MaintRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ujnbox.cheetah.common.model.ErrorCodeEnum.MAINT_RECORD_INSERT_FAILED;
import static com.ujnbox.cheetah.common.model.ErrorCodeEnum.MAINT_RECORD_QUERY_FAILED;

/**
 * MaintRecordController 处理维护记录相关请求的控制器类。
 */
@RestController
@RequestMapping("maint-records")
public class MaintRecordController {

    @Autowired
    private MaintRecordService maintRecordService;

    /**
     * 生成新的维护记录。
     *
     * @param clientName          客户姓名。
     * @param phoneNumber         客户电话号码。
     * @param unit                客户所在单元（可选，默认为空字符串）。
     * @param room                客户所在房间。
     * @param buildingId          客户所在建筑的ID。
     * @param adderId             添加维护记录的人员ID。
     * @param maintType           维护类型。
     * @param maintDescription    维护描述。
     * @param locationDescription 维护地点描述。
     * @return 响应消息对象，包含操作的结果。
     */
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

        // 调用维护记录服务方法生成新的维护记录
        boolean success = maintRecordService.generateNewRecord(clientName, phoneNumber, unit, room, buildingId, adderId, maintType, maintDescription, locationDescription);

        // 根据服务方法的返回值返回对应的响应消息
        if (success) {
            return ResponseMsg.success();
        } else {
            return ResponseMsg.error(MAINT_RECORD_INSERT_FAILED);
        }
    }

    /**
     * 根据完成情况和状态查询维护记录列表。
     *
     * @param status 完成情况。
     * @param state  状态。
     * @return 响应消息对象，包含操作的结果或维护记录列表。
     */
    @PostMapping("/list")
    public ResponseMsg<?> listByStatusAndState(@RequestParam(value = "status") Integer status,
                                               @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        // 调用维护记录服务方法获取维护记录列表
        List<MaintRecordVo> maintRecordVos = maintRecordService.listByStatusAndState(status, state);

        // 根据服务方法的返回值返回对应的响应消息
        return maintRecordVos != null ? ResponseMsg.success(maintRecordVos) : ResponseMsg.error(MAINT_RECORD_QUERY_FAILED);
    }
}
