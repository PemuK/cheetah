package com.ujnbox.cheetah.controller;

import com.github.pagehelper.PageInfo;
import com.ujnbox.cheetah.common.annotation.RequiresPermission;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.dox.BuildingDo;
import com.ujnbox.cheetah.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ujnbox.cheetah.common.model.ErrorCodeEnum.ADD_ERROR;
import static com.ujnbox.cheetah.common.model.ErrorCodeEnum.UPDATE_ERROR;

@RestController
@RequestMapping("/building")
public class BuildingController {

    @Autowired
    BuildingService buildingService;

    @GetMapping("/page")
    public ResponseMsg<PageInfo> pageByStatus(@RequestParam(value = "pageNum") Integer pageNum,
                                              @RequestParam(value = "pageSize") Integer pageSize,
                                              @RequestParam(value = "status", required = false, defaultValue = "1") Integer status) {
        return buildingService.pageByStatus(pageNum, pageSize, status);
    }

    @GetMapping("/name")
    public ResponseMsg<PageInfo> pageByName(@RequestParam(value = "pageNum") Integer pageNum,
                                              @RequestParam(value = "pageSize") Integer pageSize,
                                              @RequestParam(value="name")String name,
                                              @RequestParam(value = "status", required = false, defaultValue = "1") Integer status) {
        return buildingService.pageByName(pageNum, pageSize, name,status);
    }

    @GetMapping("/list")
    public ResponseMsg<List<BuildingDo>> listByStatus(
                                             @RequestParam(value = "status", required = false, defaultValue = "1") Integer status) {
        return buildingService.listByStatus(status);
    }

    @PostMapping("/update")
    @RequiresPermission(3)
    public ResponseMsg<?> updateBuilding(@RequestParam Integer buildingId,
                                         @RequestParam String buildingName,
                                         @RequestParam Integer type,
                                         @RequestParam Integer campus) {
        boolean success = buildingService.update(buildingId, buildingName, type, campus);
        return success ? ResponseMsg.success(true) : ResponseMsg.error(UPDATE_ERROR);
    }

    @PostMapping("/updateStatus")
    @RequiresPermission(3)
    public ResponseMsg<?> updateStatus(@RequestParam Integer id,
                                       @RequestParam Integer status) {
        boolean success = buildingService.updateStatusById(id, status);
        return success ? ResponseMsg.success(true) : ResponseMsg.error(UPDATE_ERROR);
    }

    @PostMapping("/add")
    @RequiresPermission(3)
    public ResponseMsg<?> addBuilding(@RequestParam String buildingName,
                                      @RequestParam Integer type,
                                      @RequestParam Integer campus) {
        boolean success = buildingService.add(buildingName, type, campus);
        return success ? ResponseMsg.success(true) : ResponseMsg.error(ADD_ERROR);
    }
}
