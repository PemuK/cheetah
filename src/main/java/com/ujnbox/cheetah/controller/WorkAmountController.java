package com.ujnbox.cheetah.controller;

import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.vo.WorkAmountVo;
import com.ujnbox.cheetah.service.WorkAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.ujnbox.cheetah.common.model.ErrorCodeEnum.NULL_ERROR;

@RestController
@RequestMapping("/work-amount")
public class WorkAmountController {
    @Autowired
    WorkAmountService workAmountService;

    @GetMapping("/person")
    public ResponseMsg<?> getPersonnelWorkAmount(@RequestParam("userId") Integer userId,
                                                 @RequestParam("startTime") LocalDateTime startTime,
                                                 @RequestParam("endTime") LocalDateTime endTime,
                                                 @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        WorkAmountVo workAmountVo = workAmountService.getPersonnelWorkAmount(userId, startTime, endTime, state);
        if (workAmountVo != null) {

            return ResponseMsg.success(workAmountVo);
        } else {
            return ResponseMsg.error(NULL_ERROR);
        }
    }

    @GetMapping("/level")
    public ResponseMsg<?> listAllWorkAmountByLevel(@RequestParam("startYears") String startYears,
                                                   @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        List<WorkAmountVo> workAmountVoList = workAmountService.listWorkAmountByStartYear(startYears, state);
        if (workAmountVoList != null && !workAmountVoList.isEmpty()) {
            return ResponseMsg.success(workAmountVoList);
        } else {
            return ResponseMsg.error(NULL_ERROR);
        }
    }

//    @GetMapping("/persons")
//    public ResponseMsg<?> listWorkAmountByIds(@RequestParam("userIds") Integer[] userIds,
//                                                   @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
//        List<WorkAmountVo> workAmountVoList = workAmountService.listWorkAmountByStartYear(startYears, state);
//        if (workAmountVoList != null && !workAmountVoList.isEmpty()) {
//            return ResponseMsg.success(workAmountVoList);
//        } else {
//            return ResponseMsg.error(NULL_ERROR);
//        }
//    }

    @GetMapping("/time")
    public ResponseMsg<?> listAllWorkAmountByTime(@RequestParam("startTime") LocalDateTime startTime,
                                                  @RequestParam("endTime") LocalDateTime endTime,
                                                  @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        List<WorkAmountVo> workAmountVoList = workAmountService.listWorkAmountByTime(startTime, endTime, state);
        if (workAmountVoList != null && !workAmountVoList.isEmpty()) {
            return ResponseMsg.success(workAmountVoList);
        } else {
            return ResponseMsg.error(NULL_ERROR);
        }
    }

    @GetMapping("/total")
    public ResponseMsg<?> getTotalWorkAmount(@RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
//         Call the service method to get the total work amount
        WorkAmountVo workAmountVo = workAmountService.getTotalWorkAmount(state);

        // Check if the result is not null and not empty
        if (workAmountVo != null) {
            return ResponseMsg.success(workAmountVo);
        } else {
            return ResponseMsg.error(NULL_ERROR);
        }
    }

    @GetMapping("/week")
    public ResponseMsg<?> getWeekWorkAmount(@RequestParam("time") LocalDateTime time,
                                            @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        WorkAmountVo workAmountVo = workAmountService.getWeekWorkAmount(time, state);

        if (workAmountVo != null) {
            return ResponseMsg.success(workAmountVo);
        } else {
            return ResponseMsg.error(NULL_ERROR);
        }
    }

    @GetMapping("/month")
    public ResponseMsg<?> getMonthWorkAmount(@RequestParam("time") LocalDateTime time,
                                             @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        // Call the service method to get the total work amount
        WorkAmountVo workAmountVo = workAmountService.getMonthWorkAmount(time, state);

        // Check if the result is not null and not empty
        if (workAmountVo != null) {
            return ResponseMsg.success(workAmountVo);
        } else {
            return ResponseMsg.error(NULL_ERROR);
        }
    }

    @GetMapping("/month-list")
    public ResponseMsg<?> listByMonth(@RequestParam("time") LocalDateTime time,
                                      @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        // Call the service method to get the total work amount
        List<WorkAmountVo> workAmountVoList = workAmountService.listWorkAmountByMonth(state, time);

        // Check if the result is not null and not empty
        if (workAmountVoList != null) {
            return ResponseMsg.success(workAmountVoList);
        } else {
            return ResponseMsg.error(NULL_ERROR);
        }
    }

    @GetMapping("/persons")
    public ResponseMsg<?> listWorkAmountByIds(@RequestParam("userIds") String userIds,
                                              @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        List<WorkAmountVo> workAmountVoList = workAmountService.listWorkAmountByIds(userIds, state);
        if (workAmountVoList != null && !workAmountVoList.isEmpty()) {
            return ResponseMsg.success(workAmountVoList);
        } else {
            return ResponseMsg.error(NULL_ERROR);
        }
    }

}
