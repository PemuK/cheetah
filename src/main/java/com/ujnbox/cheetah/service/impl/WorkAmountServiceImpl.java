package com.ujnbox.cheetah.service.impl;

import com.ujnbox.cheetah.dao.MaintRecordFinishedDao;
import com.ujnbox.cheetah.dao.MaintTypeDao;
import com.ujnbox.cheetah.dao.UserDao;
import com.ujnbox.cheetah.model.dox.MaintTypeDo;
import com.ujnbox.cheetah.model.dox.UserDo;
import com.ujnbox.cheetah.model.vo.MaintRecordVo;
import com.ujnbox.cheetah.model.vo.UserVo;
import com.ujnbox.cheetah.model.vo.WorkAmountVo;
import com.ujnbox.cheetah.service.WorkAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkAmountServiceImpl implements WorkAmountService {

    @Autowired
    UserDao userDao;

    @Autowired
    MaintTypeDao maintTypeDao;

    @Autowired
    MaintRecordFinishedDao maintRecordFinishedDao;

    @Override
    public List<WorkAmountVo> listWorkAmountByStartYear(String startYears, Integer state) {
        List<WorkAmountVo> workAmountVoList = new ArrayList<>();

        try {
            // Parse startYears string into individual years
            List<String> yearList = Arrays.asList(startYears.split("\\s*,\\s*"));

            // Fetch all maintenance types once
            List<MaintTypeDo> maintTypeDos = maintTypeDao.list();
            Map<Integer, Integer> maintTypeWorkAmountMap = maintTypeDos.stream()
                    .collect(Collectors.toMap(MaintTypeDo::getId, MaintTypeDo::getWorkAmount));

            // Iterate over each year and fetch users
            for (String year : yearList) {
                List<UserVo> users = userDao.listByStartYear(Integer.parseInt(year), state);

                // Calculate work amounts for each user
                for (UserVo user : users) {
                    Integer workAmount = 0;
                    Integer recordAmount = 0;

                    // Fetch finished maintenance records
                    List<MaintRecordVo> maintRecordVoList = maintRecordFinishedDao.listByStateAndId(state, user.getId());

                    // Process records
                    for (MaintRecordVo maintRecordVo : maintRecordVoList) {
                        Integer workAmountForType = maintTypeWorkAmountMap.get(maintRecordVo.getMaintType());
                        if (workAmountForType != null) {
                            workAmount += workAmountForType;
                        }
                        recordAmount += 1;
                    }

                    // Create WorkAmountVo
                    WorkAmountVo workAmountVo = new WorkAmountVo().builder()
                            .userId(user.getId())
                            .name(user.getName())
                            .startYear(user.getStartYear())
                            .recordAmount(recordAmount)
                            .workAmount(workAmount)
                            .build();

                    if (workAmountVo != null) {
                        workAmountVoList.add(workAmountVo);
                    }
                }
            }

            // Sort the results based on the desired criteria, e.g., by total work amount
            workAmountVoList.sort(Comparator.comparingInt(WorkAmountVo::getWorkAmount).reversed());

        } catch (Exception e) {
            // Log the exception (use proper logging mechanism in production code)
            // Consider using a logging framework like SLF4J or Log4j
            System.err.println("Error while calculating work amounts: " + e.getMessage());
            e.printStackTrace();
            // Optionally, handle or rethrow the exception based on your application needs
        }

        return workAmountVoList;
    }

    @Override
    public List<WorkAmountVo> listWorkAmountByTime(LocalDateTime startTime, LocalDateTime endTime, Integer state) {
        List<WorkAmountVo> workAmountVoList = new ArrayList<>();

        List<MaintTypeDo> maintTypeDos = maintTypeDao.list();
        List<MaintRecordVo> maintRecordVoList = maintRecordFinishedDao.listByStateAndTimeRange(state, startTime, endTime);
        List<UserVo> users = userDao.listByTime(startTime.getYear() - 2, state);
        Map<Integer, Integer> maintTypeWorkAmountMap = maintTypeDos.stream()
                .collect(Collectors.toMap(MaintTypeDo::getId, MaintTypeDo::getWorkAmount));
        for (UserVo user : users) {
            Integer workAmount = 0;
            Integer recordAmount = 0;

            // Process records
            for (MaintRecordVo maintRecordVo : maintRecordVoList) {
                if (maintRecordVo.getCompleter() != null && maintRecordVo.getCompleter().contains(String.valueOf(user.getId()))) {
                    Integer workAmountForType = maintTypeWorkAmountMap.get(maintRecordVo.getMaintType());
                    if (workAmountForType != null) {
                        workAmount += workAmountForType;
                    }
                    recordAmount += 1;
                }
            }
            // Create WorkAmountVo
            WorkAmountVo workAmountVo = new WorkAmountVo().builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .startYear(user.getStartYear())
                    .recordAmount(recordAmount)
                    .workAmount(workAmount)
                    .build();

            if (workAmountVo != null && workAmountVo.getRecordAmount() != 0) {
                workAmountVoList.add(workAmountVo);
            }
        }
        workAmountVoList.sort(Comparator.comparingInt(WorkAmountVo::getWorkAmount).reversed());
        return workAmountVoList;
    }

    @Override
    public WorkAmountVo getWeekWorkAmount(LocalDateTime time, Integer state) {
        // Ensure the provided time is not null
        if (time == null) {
            throw new IllegalArgumentException("Time must not be null");
        }

        // Calculate the start of the week (Monday) for the given time
        LocalDateTime thisMonday = time.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .with(LocalTime.MIN);

        // Calculate the start of the next week (Monday)
        LocalDateTime nextMonday = thisMonday.plusWeeks(1);

        // Ensure the state is not null or invalid
        if (state == null) {
            throw new IllegalArgumentException("State must not be null");
        }

        // Fetch the total work amount for the calculated week and state
        return getTotalWorkAmount(thisMonday, nextMonday, state);
    }


    @Override
    public WorkAmountVo getMonthWorkAmount(LocalDateTime time, Integer state) {
        LocalDateTime firstDayOfThisMonth = time.with(TemporalAdjusters.firstDayOfMonth());

        LocalDateTime firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);

        return getTotalWorkAmount(firstDayOfThisMonth, firstDayOfNextMonth, state);
    }

    @Override
    public WorkAmountVo getTotalWorkAmount(Integer state) {
        Integer workAmount = 0;
        Integer recordAmount = 0;
        Map<String, Integer> map = new HashMap<>();
        map.put("state", state);
        List<MaintRecordVo> maintRecordVoList = maintRecordFinishedDao.listByState(map);
        List<MaintTypeDo> maintTypeDos = maintTypeDao.list();

        // Create a map for fast lookup of MaintTypeDo by ID
        Map<Integer, Integer> maintTypeWorkAmountMap = maintTypeDos.stream()
                .collect(Collectors.toMap(MaintTypeDo::getId, MaintTypeDo::getWorkAmount));

        // Process records
        for (MaintRecordVo maintRecordVo : maintRecordVoList) {
            Integer workAmountForType = maintTypeWorkAmountMap.get(maintRecordVo.getMaintType());
            if (workAmountForType != null) {
                workAmount += workAmountForType;
            }
            recordAmount += 1;
        }

        // Create WorkAmountVo
        WorkAmountVo workAmountVo = new WorkAmountVo().builder()
                .recordAmount(recordAmount)
                .workAmount(workAmount)
                .build();

        return workAmountVo;
    }

    @Override
    public List<WorkAmountVo> listWorkAmountByMonth(Integer state, LocalDateTime time) {
        LocalDateTime firstDayOfThisMonth = time.with(TemporalAdjusters.firstDayOfMonth());

        LocalDateTime firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);
        return listWorkAmountByTime(firstDayOfThisMonth, firstDayOfNextMonth, state);
    }

//    @Override
//    public List<WorkAmountVo> listWorkAmountByIds(Integer[] userIds, Integer state) {
//        List<WorkAmountVo>workAmountVoList = new ArrayList<>();
//        List<MaintRecordVo> maintRecordVoList = maintRecordFinishedDao.listByStateAndTimeRange();
//        for (Integer userId : userIds) {
//
//        }
//        return null;
//    }

    @Override
    public WorkAmountVo getPersonnelWorkAmount(Integer userId, LocalDateTime startTime, LocalDateTime endTime, Integer state) {
        UserDo userDo = userDao.getById(userId);
        Integer workAmount = 0;
        Integer recordAmount = 0;
        if (userDo != null) {
            // Fetch finished maintenance records and types
            List<MaintRecordVo> maintRecordVoList = maintRecordFinishedDao.listByStateAndIdAndTime(state, startTime, endTime, userId);
            List<MaintTypeDo> maintTypeDos = maintTypeDao.list();

            // Create a map for fast lookup of MaintTypeDo by ID
            Map<Integer, Integer> maintTypeWorkAmountMap = maintTypeDos.stream()
                    .collect(Collectors.toMap(MaintTypeDo::getId, MaintTypeDo::getWorkAmount));

            // Process records
            for (MaintRecordVo maintRecordVo : maintRecordVoList) {
                Integer workAmountForType = maintTypeWorkAmountMap.get(maintRecordVo.getMaintType());
                if (workAmountForType != null) {
                    workAmount += workAmountForType;
                }
                recordAmount += 1;
            }

            // Create WorkAmountVo
            WorkAmountVo workAmountVo = new WorkAmountVo().builder()
                    .userId(userId)
                    .name(userDo.getName())
                    .startYear(userDo.getStartYear())
                    .recordAmount(recordAmount)
                    .workAmount(workAmount)
                    .build();

            return workAmountVo;
        } else {
            return null;
        }

    }

    @Override
    public List<WorkAmountVo> listWorkAmountByIds(String userIds, Integer state) {
        List<WorkAmountVo> workAmountVoList = new ArrayList<>();

        // Parse userIds string into a list of Integer
        List<Integer> userIdList = Arrays.stream(userIds.split("\\s*,\\s*"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Fetch maintenance types once to avoid repeated database calls
        List<MaintTypeDo> maintTypeDos = maintTypeDao.list();
        Map<Integer, Integer> maintTypeWorkAmountMap = maintTypeDos.stream()
                .collect(Collectors.toMap(MaintTypeDo::getId, MaintTypeDo::getWorkAmount));

        // Iterate over each user ID
        for (Integer userId : userIdList) {
            UserDo userDo = userDao.getById(userId);
            if (userDo == null) {
                continue; // Skip if user is not found
            }

            Integer workAmount = 0;
            Integer recordAmount = 0;

            // Fetch finished maintenance records for this user and state
            List<MaintRecordVo> maintRecordVoList = maintRecordFinishedDao.listByStateAndId(state, userId);

            // Process records
            for (MaintRecordVo maintRecordVo : maintRecordVoList) {
                Integer workAmountForType = maintTypeWorkAmountMap.get(maintRecordVo.getMaintType());
                if (workAmountForType != null) {
                    workAmount += workAmountForType;
                }
                recordAmount += 1;
            }

            // Create WorkAmountVo for each user
            WorkAmountVo workAmountVo = new WorkAmountVo().builder()
                    .userId(userId)
                    .name(userDo.getName())
                    .startYear(userDo.getStartYear())
                    .recordAmount(recordAmount)
                    .workAmount(workAmount)
                    .build();

            workAmountVoList.add(workAmountVo);
        }

        // Optionally, sort the result list by work amount or other criteria
        workAmountVoList.sort(Comparator.comparingInt(WorkAmountVo::getWorkAmount).reversed());

        return workAmountVoList;
    }


    private WorkAmountVo getTotalWorkAmount(LocalDateTime startTime, LocalDateTime endTime, Integer state) {
        // Fetch all finished maintenance records within the time range and state
        List<MaintRecordVo> maintRecordVoList = maintRecordFinishedDao.listByStateAndTimeRange(state, startTime, endTime);

        // Fetch all maintenance types to map their IDs to work amounts
        List<MaintTypeDo> maintTypeDos = maintTypeDao.list();
        Map<Integer, Integer> maintTypeWorkAmountMap = maintTypeDos.stream()
                .collect(Collectors.toMap(MaintTypeDo::getId, MaintTypeDo::getWorkAmount));

        // Calculate total work amount and record count
        int totalWorkAmount = 0;
        int totalRecordCount = 0;

        for (MaintRecordVo maintRecordVo : maintRecordVoList) {
            Integer workAmountForType = maintTypeWorkAmountMap.get(maintRecordVo.getMaintType());
            if (workAmountForType != null) {
                totalWorkAmount += workAmountForType;
            }
            totalRecordCount++;
        }

        // Create WorkAmountVo for total work amount
        WorkAmountVo totalWorkAmountVo = new WorkAmountVo().builder()
                .userId(null) // No specific user
                .name("Total")
                .startYear(null) // No specific start year
                .recordAmount(totalRecordCount)
                .workAmount(totalWorkAmount)
                .build();

        return totalWorkAmountVo;
    }

}
