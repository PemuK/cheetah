package com.ujnbox.cheetah.controller;

import com.ujnbox.cheetah.common.annotation.RequiresPermission;
import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.vo.UserVo;
import com.ujnbox.cheetah.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public ResponseMsg<?> list(@RequestParam(value = "pageNum") Integer pageNum,
                               @RequestParam(value = "pageSize") Integer pageSize,
                               @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        return ResponseMsg.success(userService.pageByState(pageNum, pageSize, state));
    }

    @GetMapping("/name")
    public ResponseMsg<?> listByName(@RequestParam(value = "pageNum") Integer pageNum,
                                     @RequestParam(value = "pageSize") Integer pageSize,
                                     @RequestParam(value = "name") String name,
                                     @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        return ResponseMsg.success(userService.listByNameAndState(pageNum, pageSize, name, state));
    }

    /**
     * 根据用户状态获取用户列表。
     *
     * @param status 用户状态。
     * @return 返回包含用户信息的 ResponseMsg 对象。
     */
    @GetMapping("/list-status")
    public ResponseMsg<List<UserVo>> listByStatus(@RequestParam(value = "status") Integer status,
                                                  @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        return userService.listByStatusAndState(status, state);
    }

//    @GetMapping("/page-state")
//    public ResponseMsg<List<UserVo>> listByState(@RequestParam(value = "state") Integer state) {
//        return ResponseMsg.success(userService.pageByState(state));
//    }

    @GetMapping("/start-year")
    public ResponseMsg<List<String>> getAllStartYear(@RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        return ResponseMsg.success(userService.getAllStartYears(state));
    }

    @GetMapping("/list-years")
    public ResponseMsg<Map<String, List<UserVo>>> listUsersByStartYear(@RequestParam(value = "status", required = false, defaultValue = "1") Integer status,
                                                                       @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        return ResponseMsg.success(userService.listByStartYear(status, state));
    }

    @PostMapping("/add")
    @RequiresPermission(3)
    public ResponseMsg<?> add(@RequestParam("name") String name,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("level") Integer level,
                              @RequestParam("phoneNumber") String phoneNumber,
                              @RequestParam("organizationId") Integer organizationId,
                              @RequestParam("status") Integer status) {
        return userService.addUser(username, name, password, phoneNumber, level, organizationId, status);
    }

    @PostMapping("/update")
    @RequiresPermission(3)
    public ResponseMsg updateById(@RequestParam("id") Integer id,
                                  @RequestParam("name") String name,
                                  @RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  @RequestParam("level") Integer level,
                                  @RequestParam("phoneNumber") String phoneNumber,
                                  @RequestParam("organizationId") Integer organizationId,
                                  @RequestParam("status") Integer status) {
        return userService.updateById(id, name, username, organizationId, password, level, phoneNumber, status) ? ResponseMsg.success() : ResponseMsg.error(ErrorCodeEnum.UPDATE_ERROR);
    }


    @PostMapping("/modify-state")
    @RequiresPermission(3)
    public ResponseMsg updateStateById(@RequestParam("userId") Integer userId,
                                       @RequestParam(value = "state", required = false, defaultValue = "1") Integer state) {
        return userService.updateStateById(userId, state) ? ResponseMsg.success() : ResponseMsg.error(ErrorCodeEnum.UPDATE_ERROR);
    }


}
