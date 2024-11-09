package com.ujnbox.cheetah.service;

import com.ujnbox.cheetah.model.vo.WorkAmountVo;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkAmountService {
    List<WorkAmountVo> listWorkAmountByStartYear(String startYears, Integer state);

    WorkAmountVo getPersonnelWorkAmount(Integer userId, LocalDateTime startTime, LocalDateTime endTime, Integer state);

    List<WorkAmountVo> listWorkAmountByTime(LocalDateTime startTime, LocalDateTime endTime, Integer state);

    WorkAmountVo getWeekWorkAmount(LocalDateTime time, Integer state);

    WorkAmountVo getMonthWorkAmount(LocalDateTime time, Integer state);

    WorkAmountVo getTotalWorkAmount(Integer state);

    List<WorkAmountVo> listWorkAmountByMonth(Integer state, LocalDateTime time);

    List<WorkAmountVo> listWorkAmountByIds(String userIds, Integer state);



}
