package com.ujnbox.cheetah.controller;

import com.github.pagehelper.PageInfo;
import com.ujnbox.cheetah.common.annotation.RequiresPermission;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.vo.MaintRecordVo;
import com.ujnbox.cheetah.service.MaintRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.ujnbox.cheetah.common.model.ErrorCodeEnum.*;

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
     * @param maintDescription    维护描述。
     * @param locationDescription 维护地点描述。
     * @return 响应消息对象，包含操作的结果。
     */
    @PostMapping("/add")
    @RequiresPermission(2)
    public ResponseMsg generateNewRecord(@RequestParam(value = "clientId", required = false) Integer clientId,
                                         @RequestParam(value = "clientName") String clientName,
                                         @RequestParam(value = "phoneNumber") String phoneNumber,
                                         @RequestParam(value = "unit", required = false, defaultValue = "") String unit,
                                         @RequestParam(value = "room") String room,
                                         @RequestParam(value = "buildingId") Integer buildingId,
                                         @RequestParam(value = "adderId") Integer adderId,
                                         @RequestParam(value = "maintDescription") String maintDescription,
                                         @RequestParam(value = "locationDescription") String locationDescription) {

        // 调用维护记录服务方法生成新的维护记录
        boolean success = maintRecordService.generateNewRecord(clientId, clientName, phoneNumber, unit, room, buildingId, adderId, maintDescription, locationDescription);

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
     * @param state 状态。
     * @return 响应消息对象，包含操作的结果或维护记录列表。
     */
    @GetMapping("/finished-list")
    public ResponseMsg<?> getFinishedMaints(@RequestParam(value = "pageNum") Integer pageNum,
                                            @RequestParam(value = "pageSize") Integer pageSize,
                                            @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        // 调用维护记录服务方法获取维护记录列表
        PageInfo<MaintRecordVo> maintRecordVos = maintRecordService.getFinishedMaints(pageNum, pageSize, state);

        // 根据服务方法的返回值返回对应的响应消息
        return maintRecordVos != null ? ResponseMsg.success(maintRecordVos) : ResponseMsg.error(MAINT_RECORD_QUERY_FAILED);
    }

    @GetMapping("/unfinished-list")
    public ResponseMsg<?> getUnfinishedMaints(@RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        // 调用维护记录服务方法获取维护记录列表
        List<MaintRecordVo> maintRecordVos = maintRecordService.getUnfinishedMaints(state);

        // 根据服务方法的返回值返回对应的响应消息
        return maintRecordVos != null ? ResponseMsg.success(maintRecordVos) : ResponseMsg.error(MAINT_RECORD_QUERY_FAILED);
    }

    @PostMapping("/report")
    public ResponseMsg maintReport(@RequestParam(value = "maintRecordId") Integer maintRecordId,
                                   @RequestParam(value = "reporterId") Integer reporterId,
                                   @RequestParam(value = "players") String players,
                                   @RequestParam(value = "maintType") Integer maintType,
                                   @RequestParam(value = "note") String note,
                                   @RequestParam(value = "status", required = true, defaultValue = "1") Integer status) {

        boolean success = maintRecordService.report(maintRecordId, reporterId, players, maintType, note, status);
        if (success) {
            return ResponseMsg.success();
        } else {
            return ResponseMsg.error(MAINT_RECORD_REPORT_FAILED);
        }
    }


    @PostMapping("/del")
    @RequiresPermission(2)
    public ResponseMsg delMaint(@RequestParam(value = "maintRecordId") Integer maintRecordId,
                                @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {

        boolean success = maintRecordService.updateState(maintRecordId, state);
        if (success) {
            return ResponseMsg.success();
        } else {
            return ResponseMsg.error(MAINT_RECORD_REPORT_FAILED);
        }
    }

    @PostMapping("/modify")
    public ResponseMsg modifyMaint(@RequestParam(value = "userId") Integer userId,
                                   @RequestParam(value = "maintRecordId") Integer maintRecordId,
                                   @RequestParam(value = "clientId") Integer clientId,
                                   @RequestParam(value = "clientName") String clientName,
                                   @RequestParam(value = "phoneNumber") String phoneNumber,
                                   @RequestParam(value = "buildingId") Integer buildingId,
                                   @RequestParam(value = "unit") String unit,
                                   @RequestParam(value = "room") String room,
                                   @RequestParam(value = "maintDescription") String maintDescription,
                                   @RequestParam(value = "locationDescription") String locationDescription,
                                   @RequestParam(value = "players", required = false) String players,
                                   @RequestParam(value = "maintType", required = false) Integer maintType,
                                   @RequestParam(value = "note", required = false) String note,
                                   @RequestParam(value = "status", required = false) Integer status) {

        return maintRecordService.updateMaint(userId, maintRecordId, clientId, clientName, phoneNumber, buildingId, unit, room, maintDescription, locationDescription, players, maintType, note, status);

    }

    /**
     * 根据建筑物、单元和房间查询维护记录列表。
     *
     * @param searchTerm 搜索条件（包括建筑物、单元和房间）。
     * @param state      状态。
     * @return 响应消息对象，包含操作的结果或维护记录列表。
     */
    @GetMapping("/page/location")
    public ResponseMsg<?> pageByBuildingUnitRoom(@RequestParam(value = "pageNum") Integer pageNum,
                                                 @RequestParam(value = "pageSize") Integer pageSize,
                                                 @RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                 @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {
        PageInfo pageInfo = maintRecordService.listByBuildingUnitRoom(pageNum, pageSize, searchTerm, state);
        return pageInfo != null ? ResponseMsg.success(pageInfo) : ResponseMsg.error(MAINT_RECORD_QUERY_FAILED);
    }

    @GetMapping("/page/client")
    public ResponseMsg<?> pageByClientName(@RequestParam(value = "pageNum") Integer pageNum,
                                           @RequestParam(value = "pageSize") Integer pageSize,
                                           @RequestParam(value = "clientName") String clientName,
                                           @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {
        PageInfo pageInfo = maintRecordService.pageByClientName(pageNum, pageSize, clientName, state);
        return pageInfo != null ? ResponseMsg.success(pageInfo) : ResponseMsg.error(MAINT_RECORD_QUERY_FAILED);
    }


    @GetMapping("/page/type")
    public ResponseMsg<?> pageByMaintType(@RequestParam(value = "pageNum") Integer pageNum,
                                           @RequestParam(value = "pageSize") Integer pageSize,
                                           @RequestParam(value = "typeId") Integer typeId,
                                           @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {
        PageInfo pageInfo = maintRecordService.pageByMaintType(pageNum, pageSize, typeId, state);
        return pageInfo != null ? ResponseMsg.success(pageInfo) : ResponseMsg.error(MAINT_RECORD_QUERY_FAILED);
    }

    @GetMapping("/page/phone")
    public ResponseMsg<?> pageByPhoneNumber(@RequestParam(value = "pageNum") Integer pageNum,
                                          @RequestParam(value = "pageSize") Integer pageSize,
                                          @RequestParam(value = "phoneNumber") String phoneNumber,
                                          @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {
        PageInfo pageInfo = maintRecordService.pageByPhoneNumber(pageNum, pageSize, phoneNumber, state);
        return pageInfo != null ? ResponseMsg.success(pageInfo) : ResponseMsg.error(MAINT_RECORD_QUERY_FAILED);
    }

    @GetMapping("/page/question")
    public ResponseMsg<?> pageByQuestion(@RequestParam(value = "pageNum") Integer pageNum,
                                            @RequestParam(value = "pageSize") Integer pageSize,
                                            @RequestParam(value = "question") String question,
                                            @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {
        PageInfo pageInfo = maintRecordService.pageByDescription(pageNum, pageSize, question, state);
        return pageInfo != null ? ResponseMsg.success(pageInfo) : ResponseMsg.error(MAINT_RECORD_QUERY_FAILED);
    }



    @GetMapping("/get")
    public ResponseMsg<List<MaintRecordVo>> modifyMaint(@RequestParam(value = "userId") Integer userId,
                                                        @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {

        return ResponseMsg.success(maintRecordService.getByIdAndState(userId, state));
    }

    @GetMapping("/client")
    public ResponseMsg<List<MaintRecordVo>> listByClientName(@RequestParam(value = "clientName") String clientName,
                                                             @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {
        return ResponseMsg.success(maintRecordService.listByClientName(clientName, state));
    }

    @GetMapping("/location")
    public ResponseMsg<List<MaintRecordVo>> listByLocation(@RequestParam(value = "buildingId") Integer buildingId,
                                                           @RequestParam(value = "unit") String unit,
                                                           @RequestParam(value = "room") String room,
                                                           @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {
        return ResponseMsg.success(maintRecordService.listByLocation(buildingId, unit, room, state));
    }

    @GetMapping("/phone-number")
    public ResponseMsg<List<MaintRecordVo>> listByPhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber,
                                                              @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {
        return ResponseMsg.success(maintRecordService.listByPhoneNumber(phoneNumber, state));
    }

    @GetMapping("/persons-month")
    public ResponseMsg<List<MaintRecordVo>> listByMonth(@RequestParam(value = "time")LocalDateTime time,
                                                        @RequestParam(value="userId")Integer userId,
                                                        @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {
        return ResponseMsg.success(maintRecordService.listByIdAndMonth(time,userId, state));
    }

    @GetMapping("/persons-time")
    public ResponseMsg<List<MaintRecordVo>> listByTime(@RequestParam(value = "startTime")LocalDateTime startTime,
                                                       @RequestParam(value = "endTime")LocalDateTime endTime,
                                                       @RequestParam(value="userId")Integer userId,
                                                       @RequestParam(value = "state", defaultValue = "1", required = false) Integer state) {
        return ResponseMsg.success(maintRecordService.listByIdAndTime(startTime,endTime,userId, state));
    }
}
