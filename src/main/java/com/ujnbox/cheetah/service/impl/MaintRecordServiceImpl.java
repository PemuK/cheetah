package com.ujnbox.cheetah.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.dao.*;
import com.ujnbox.cheetah.model.dox.MaintClientProfileDo;
import com.ujnbox.cheetah.model.dox.MaintRecordDo;
import com.ujnbox.cheetah.model.dox.UserDo;
import com.ujnbox.cheetah.model.vo.MaintRecordVo;
import com.ujnbox.cheetah.service.MaintRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ujnbox.cheetah.common.model.ErrorCodeEnum.PERMISSION_ERROR;
import static com.ujnbox.cheetah.common.model.ErrorCodeEnum.UPDATE_ERROR;

/**
 * MaintRecordService 接口的实现类。
 * 提供生成维护记录的服务。
 */
@Service
public class MaintRecordServiceImpl implements MaintRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MaintRecordServiceImpl.class);

    @Autowired
    private MaintRecordFinishedDao maintRecordFinishedDao;

    @Autowired
    private MaintRecordUnFinishedDao maintRecordUnFinishedDao;

    @Autowired
    private MaintRecordDao maintRecordDao;

    @Autowired
    private MaintClientProfileDao maintClientProfileDao;

    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MaintTypeDao maintTypeDao;

    @Override
    public boolean generateNewRecord(Integer clientId, String clientName, String phoneNumber, String unit, String room, Integer buildingId, Integer adderId, String maintDescription, String locationDescription) {
        logger.info("正在为客户生成新的维护记录: {}", clientName);

        MaintClientProfileDo maintClientProfileDo = MaintClientProfileDo.builder()
                .clientName(clientName)
                .phoneNumber(phoneNumber)
                .unit(unit)
                .room(room)
                .buildingId(buildingId)
                .build();
        // 创建客户档案记录
        if (clientId == null) {
            // 将客户档案记录插入数据库
            maintClientProfileDao.insert(maintClientProfileDo);

            // 记录客户档案创建结果
            logger.info("客户建档: {}, {}, {}, {}, {}", clientName, phoneNumber, unit, room, buildingId);
        }
        // 创建维护记录
        MaintRecordDo maintRecordDo = MaintRecordDo.builder()
                .clientId(clientId != null ? clientId : maintClientProfileDo.getId())
                .adderId(adderId)
                .maintDescription(maintDescription)
                .locationDescription(locationDescription != null && !locationDescription.isEmpty() ? locationDescription : null)
                .build();

        // 将维护记录插入数据库
        int recordInsertResult = maintRecordDao.insert(maintRecordDo);

        // 记录维护记录创建结果
        logger.info("维护记录建立: {}, {}, {}, {}", maintClientProfileDo.getId(), adderId, maintDescription, locationDescription);

        // 返回维护记录插入操作的结果
        return recordInsertResult > 0;
    }


    Map<Integer, UserDo> userCache = new HashMap<>();


    // 辅助函数来获取用户并缓存
    UserDo getUserByIdAndCache(Integer userId) {
        if (userId == null || userCache.containsKey(userId)) {
            return userCache.get(userId);
        }
        UserDo user = userDao.getById(userId);
        userCache.put(userId, user);
        return user;
    }

    @Override
    public PageInfo<MaintRecordVo> getFinishedMaints(Integer pageNum, Integer pageSize, Integer state) {
        logger.info("查询已完成维护记录 ，状态: {}", state);

        // 启动分页
        String orderBy = "create_time desc"; // 按照排序字段倒序排序
        PageHelper.startPage(pageNum, pageSize, orderBy);

        Map<String, Object> params = new HashMap<>();
        params.put("state", state);

        // 从数据库获取维护记录列表
        List<MaintRecordVo> list = maintRecordFinishedDao.listByState(params);

        // 使用 PageInfo 获取分页信息
        PageInfo<MaintRecordVo> pageInfo = new PageInfo<>(list);

        // 转换为维护记录视图对象
        toAddCompeleterInfo(list);

        return pageInfo;
    }

    @Override
    public List<MaintRecordVo> getByIdAndState(Integer id, Integer state) {
        return maintRecordFinishedDao.listByStateAndId(state, id);
    }

    @Override
    public List<MaintRecordVo> listByClientName(String clientName, Integer state) {
        return maintRecordFinishedDao.listByClientName(clientName, state);
    }

    @Override
    public List<MaintRecordVo> listByPhoneNumber(String phoneNumber, Integer state) {
        return maintRecordFinishedDao.listPhoneNumber(phoneNumber, state);
    }

    @Override
    public List<MaintRecordVo> listByLocation(Integer buildingId, String unit, String room, Integer state) {
        return maintRecordFinishedDao.listByLocation(buildingId, unit, room, state);
    }


    @Override
    public List<MaintRecordVo> getUnfinishedMaints(Integer state) {
        logger.info("查询未完成维护记录 ，状态: {}", state);
        List<MaintRecordVo> list = maintRecordUnFinishedDao.listByState(state);
        return list;
    }

    /**
     * 提交填报信息并更新数据库
     *
     * @param maintRecordId 维护记录ID
     * @param reporterId    填报人ID
     * @param players       完成人信息
     * @param maintType     维护类型
     * @param note          备注信息
     * @param status        状态信息
     * @return 如果更新成功返回true，否则返回false
     */
    @Override
    public boolean report(Integer maintRecordId, Integer reporterId, String players, Integer maintType, String note, Integer status) {

        // 记录填报备注信息
        logger.info("填报维护id：{}，填报人id：{}，备注：{}，更改完成状态：{}", maintRecordId, reporterId, note, status);

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 更新填报备注
        boolean noteUpdateSuccess = updateNote(maintRecordId, note);

        // 更新填报人信息
        boolean reporterIdUpdateSuccess = updateReporterId(maintRecordId, reporterId);

        // 更新填报状态信息
        boolean statusUpdateSuccess = updateStatus(maintRecordId, status);

        // 更新完成人员信息
        boolean completerUpdateSuccess = updateCompleter(maintRecordId, players);

        // 更新维护类型信息
        boolean typeUpdateSuccess = updateType(maintRecordId, maintType);

        // 更新完成时间
        boolean finishTimeUpdateSuccess = updateFinishTime(maintRecordId, now);

        // 返回更新结果
        return noteUpdateSuccess && statusUpdateSuccess && reporterIdUpdateSuccess && completerUpdateSuccess && typeUpdateSuccess && finishTimeUpdateSuccess;
    }

    private boolean updateFinishTime(Integer maintRecordId, LocalDateTime finishTime) {
        return maintRecordDao.updateFinishTimeById(finishTime, maintRecordId) > 0;
    }

    /**
     * 更新维护记录状态
     *
     * @param maintRecordId 维护记录ID
     * @param status        状态信息
     * @return 如果更新成功返回true，否则返回false
     */
    public boolean updateStatus(Integer maintRecordId, Integer status) {
        return maintRecordDao.updateStatusById(status, maintRecordId) > 0;
    }

    public boolean updateState(Integer maintRecordId, Integer state) {
        return maintRecordDao.updateStateById(state, maintRecordId) > 0;
    }

    @Override
    public ResponseMsg updateMaint(Integer userId, Integer maintRecordId, Integer clientId, String clientName, String phoneNumber, Integer buildingId, String unit, String room, String maintDescription, String locationDescription, String players, Integer maintType, String note, Integer status) {
        // 获取维护记录和用户信息
        MaintRecordVo maintRecordVo = maintRecordDao.getById(maintRecordId);
        UserDo userDo = getUserByIdAndCache(userId);
        boolean hasPermission = (maintRecordVo.getReporterId() == null || maintRecordVo.getReporterId().equals(userId))
                || userDo.getStatus() >= 2
                || (maintRecordVo.getCompleter() != null && maintRecordVo.getCompleter().contains(String.valueOf(userId)));

        // 如果没有权限，返回 false
        if (!hasPermission) {
            return ResponseMsg.error(PERMISSION_ERROR);
        }

        // 创建并更新客户信息对象
        MaintClientProfileDo maintClientProfileDo = MaintClientProfileDo.builder()
                .id(clientId)
                .phoneNumber(phoneNumber)
                .clientName(clientName)
                .unit(unit)
                .room(room)
                .buildingId(buildingId)
                .build();
        boolean updateClientProfileSuccess = maintClientProfileDao.updateById(maintClientProfileDo) > 0;

        // 更新维护记录的描述信息和位置描述
        boolean updateMaintRecordSuccess = maintRecordDao.updateDescById(maintDescription, locationDescription, maintRecordId) > 0;

        // 更新填报人信息，如果 players 不为 null
        boolean updatePlayersSuccess = (players != null) ? (maintRecordDao.updateCompleterById(players, maintRecordId) > 0) : true;

        // 更新维护类型，如果 maintType 不为 null
        boolean updateTypeSuccess = (maintType != null) ? (maintRecordDao.updateMaintTypeById(maintType, maintRecordId) > 0) : true;

        // 更新备注信息，如果 note 不为 null
        boolean updateNoteSuccess = (note != null) ? (maintRecordDao.updateNoteById(note, maintRecordId) > 0) : true;

        // 更新状态信息，如果 status 不为 null
        boolean updateStatusSuccess = (status != null) ? (maintRecordDao.updateStatusById(status, maintRecordId) > 0) : true;

        // 返回所有更新操作是否成功
        return updateClientProfileSuccess && updateMaintRecordSuccess && updatePlayersSuccess && updateTypeSuccess && updateNoteSuccess && updateStatusSuccess ? ResponseMsg.success() : ResponseMsg.error(UPDATE_ERROR);
    }

    @Override
    public PageInfo listByBuildingUnitRoom(Integer pageNum, Integer pageSize, String searchTerm, Integer state) {

        // 启动分页
        String orderBy = "create_time desc"; // 按照排序字段倒序排序
        PageHelper.startPage(pageNum, pageSize, orderBy);

        Map<String, Object> params = new HashMap<>();
        params.put("state", state);

        List<MaintRecordVo> list = maintRecordFinishedDao.listByBuildingUnitRoom(searchTerm, state);

        // 转换为维护记录视图对象
        toAddCompeleterInfo(list);

        return new PageInfo<>(list);
    }


    @Override
    public PageInfo pageByClientName(Integer pageNum, Integer pageSize, String clientName, Integer state) {

        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<MaintRecordVo> list = maintRecordFinishedDao.listByClientName(clientName, state);

        toAddCompeleterInfo(list);

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo pageByPhoneNumber(Integer pageNum, Integer pageSize, String phoneNumber, Integer state) {
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<MaintRecordVo> list = maintRecordFinishedDao.listPhoneNumber(phoneNumber, state);

        toAddCompeleterInfo(list);

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo pageByMaintType(Integer pageNum, Integer pageSize, Integer typeId, Integer state) {
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<MaintRecordVo> list = maintRecordFinishedDao.listByMaintType(typeId, state);

        toAddCompeleterInfo(list);

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo pageByUserId(Integer pageNum, Integer pageSize, Integer userId, Integer state) {
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<MaintRecordVo> list = maintRecordFinishedDao.listByStateAndId(state, userId);

        toAddCompeleterInfo(list);

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo pageByDescription(Integer pageNum, Integer pageSize, String description, Integer state) {
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<MaintRecordVo> list = maintRecordFinishedDao.listByMaintDescription(description, state);

        toAddCompeleterInfo(list);

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo pageByIdAndTime(Integer pageNum, Integer pageSize, LocalDateTime startTime, LocalDateTime endTime, Integer userId, Integer state) {
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<MaintRecordVo> list = maintRecordFinishedDao.listByStateAndIdAndTime(state, startTime, endTime, userId);
        toAddCompeleterInfo(list);
        return new PageInfo<>(list);
    }

    @Override
    public List<MaintRecordVo> listByIdAndMonth(LocalDateTime time, Integer userId, Integer state) {
        LocalDateTime firstDayOfThisMonth = time.with(TemporalAdjusters.firstDayOfMonth());

        LocalDateTime firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);
        List<MaintRecordVo> list = maintRecordFinishedDao.listByStateAndIdAndTime(state, firstDayOfThisMonth, firstDayOfNextMonth, userId);
        toAddCompeleterInfo(list);
        return list;
    }

    @Override
    public List<MaintRecordVo> listByIdAndTime(LocalDateTime startTime, LocalDateTime endTime, Integer userId, Integer state) {
        List<MaintRecordVo> list = maintRecordFinishedDao.listByStateAndIdAndTime(state, startTime, endTime, userId);
        toAddCompeleterInfo(list);
        return list;

    }


    private void toAddCompeleterInfo(List<MaintRecordVo> list) {
        for (MaintRecordVo maintRecordVo : list) {
            // 解析 completer 字段并从缓存中获取对应用户的名字
            String completerIds = maintRecordVo.getCompleter();
            List<String> completerNames = new ArrayList<>();
            if (completerIds != null && !completerIds.isEmpty()) {
                String[] ids = completerIds.split(",");
                for (String id : ids) {
                    try {
                        Integer userId = Integer.parseInt(id.trim());
                        UserDo completerUser = getUserByIdAndCache(userId);
                        if (completerUser != null) {
                            completerNames.add(completerUser.getName());
                        }
                    } catch (NumberFormatException e) {
                        logger.error("Invalid user ID format: {}", id, e);
                    }
                }
            }
            String completerName = String.join(",", completerNames);
            maintRecordVo.setCompleterName(completerName);
        }
    }


    /**
     * 更新填报人信息
     *
     * @param maintRecordId 维护记录ID
     * @param reporterId    填报人ID
     * @return 如果更新成功返回true，否则返回false
     */
    private boolean updateReporterId(Integer maintRecordId, Integer reporterId) {
        return maintRecordDao.updateReporterIdById(reporterId, maintRecordId) > 0;
    }

    /**
     * 更新填报备注信息
     *
     * @param maintRecordId 维护记录ID
     * @param note          备注信息
     * @return 如果更新成功返回true，否则返回false
     */
    private boolean updateNote(Integer maintRecordId, String note) {
        return maintRecordDao.updateNoteById(note, maintRecordId) > 0;
    }

    private boolean updateCompleter(Integer maintRecordId, String completers) {
        return maintRecordDao.updateCompleterById(completers, maintRecordId) > 0;
    }

    private boolean updateType(Integer maintRecordId, Integer type) {
        return maintRecordDao.updateTypeById(type, maintRecordId) > 0;
    }
}
