package com.ujnbox.cheetah.controller;

import com.ujnbox.cheetah.common.annotation.RequiresPermission;
import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.dox.MaintTypeDo;
import com.ujnbox.cheetah.service.MaintTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maint-types")
public class MaintTypeController {

    @Autowired
    private MaintTypeService maintTypeService;

    @GetMapping("/list")
    public ResponseMsg<List<MaintTypeDo>> all(@RequestParam(value = "status", defaultValue = "1", required = false) Integer status) {
        return ResponseMsg.success(maintTypeService.list(status));
    }

    @PostMapping("/update")
    @RequiresPermission(3)
    public ResponseMsg<?> update(@RequestParam("id") Integer id,
                                 @RequestParam(value = "typeName", required = false) String typeName,
                                 @RequestParam(value = "workAmount", required = false) Integer workAmount,
                                 @RequestParam(value = "status", required = false) Integer status) {
        boolean success = maintTypeService.update(id, typeName, workAmount);
        return success ? ResponseMsg.success(null) : ResponseMsg.error(ErrorCodeEnum.UPDATE_ERROR);
    }

    @PostMapping("/add")
    @RequiresPermission(3)
    public ResponseMsg<?> add(@RequestParam("typeName") String typeName,
                              @RequestParam("workAmount") Integer workAmount) {
        return ResponseMsg.success(maintTypeService.add(typeName, workAmount));
    }

    @PostMapping("/update-status")
    @RequiresPermission(3)
    public ResponseMsg<?> updateStatusById(@RequestParam("id") Integer id,
                                           @RequestParam(value = "status", defaultValue = "0", required = false) Integer status) {
        boolean success = maintTypeService.updateStatusById(id, status);
        return success ? ResponseMsg.success(null) : ResponseMsg.error(ErrorCodeEnum.UPDATE_ERROR);
    }
}
