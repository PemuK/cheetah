package com.ujnbox.cheetah.controller;

import com.ujnbox.cheetah.common.annotation.RequiresPermission;
import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.dox.OrganizationDo;
import com.ujnbox.cheetah.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织控制器，用于处理与组织相关的HTTP请求。
 */
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    /**
     * 获取所有指定状态的组织列表。
     *
     * @param status 组织状态，默认为1（有效状态）
     * @return 包含组织实体对象列表的响应消息
     */
    @GetMapping("/page")
    public ResponseMsg<?> pageByStatus(@RequestParam(value = "pageNum") Integer pageNum,
                                       @RequestParam(value = "pageSize") Integer pageSize,
                                       @RequestParam(value = "status", defaultValue = "1", required = false) Integer status) {
        return organizationService.pageByStatus(pageNum, pageSize, status);
    }

    @GetMapping("/name")
    public ResponseMsg<?> pageByName(@RequestParam(value = "pageNum") Integer pageNum,
                                     @RequestParam(value = "pageSize") Integer pageSize,
                                     @RequestParam(value = "name") String name,
                                     @RequestParam(value = "status", defaultValue = "1", required = false) Integer status) {
        return organizationService.listByName(pageNum, pageSize, name, status);
    }

    @GetMapping("/list")
    public ResponseMsg<?> listByStatus(
            @RequestParam(value = "status", defaultValue = "1", required = false) Integer status) {
        return organizationService.listByStatus(status);
    }

    /**
     * 更新组织的名称。
     *
     * @param id               组织ID
     * @param organizationName 新的组织名称
     * @return 成功或失败的响应消息
     */
    @PostMapping("/update-name")
    @RequiresPermission(3)
    public ResponseMsg<?> updateOrganizationName(@RequestParam("id") Integer id,
                                                 @RequestParam(value = "organizationName", required = false) String organizationName) {
        boolean success = organizationService.updateOrganizationNameById(organizationName, id);
        return success ? ResponseMsg.success(null) : ResponseMsg.error(ErrorCodeEnum.UPDATE_ERROR);
    }

    /**
     * 添加一个新的组织。
     *
     * @param organizationName 组织名称
     * @return 添加结果的响应消息
     */
    @PostMapping("/add")
    @RequiresPermission(3)
    public ResponseMsg<?> addOrganization(@RequestParam("organizationName") String organizationName) {
        OrganizationDo organizationDo = new OrganizationDo();
        organizationDo.setOrganizationName(organizationName);
        return ResponseMsg.success(organizationService.addOrganization(organizationDo));
    }

    /**
     * 更新组织的状态。
     *
     * @param id     组织ID
     * @param status 组织状态，默认为0（无效状态）
     * @return 成功或失败的响应消息
     */
    @PostMapping("/update-status")
    @RequiresPermission(3)
    public ResponseMsg<?> updateStatusById(@RequestParam("id") Integer id,
                                           @RequestParam(value = "status", defaultValue = "0", required = false) Integer status) {
        boolean success = organizationService.updateStatusById(status, id);
        return success ? ResponseMsg.success(null) : ResponseMsg.error(ErrorCodeEnum.UPDATE_ERROR);
    }

    /**
     * 根据ID和状态获取组织信息。
     *
     * @param id     组织ID
     * @param status 组织状态
     * @return 包含组织实体对象的响应消息
     */
    @GetMapping("/get")
    public ResponseMsg<?> getOrganizationByIdAndStatus(@RequestParam("id") Integer id,
                                                       @RequestParam("status") Integer status) {
        OrganizationDo organizationDo = organizationService.getByIdAndStatus(id, status);
        return organizationDo != null ? ResponseMsg.success(organizationDo) : ResponseMsg.error(ErrorCodeEnum.NULL_ERROR);
    }
}
