package com.ujnbox.cheetah.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {
    SYSTEM_ERROR(-999, "系统错误"),


    //维护记录相关错误代码
    MAINT_RECORD_INSERT_FAILED(1001, "维护记录插入失败"),
    MAINT_RECORD_QUERY_FAILED(1002, "查询维护记录失败"),
    MAINT_RECORD_REPORT_FAILED(1003, "填报维护记录失败"),
    MAINT_RECORD_MODIFY_FAILED(1004, "修改维护记录失败"),

    //数据操作错误码
    NULL_ERROR(1005, "请求数据为空"),
    UPDATE_ERROR(1006, "更新数据失败"),
    ADD_ERROR(1007, "添加失败"),
    PERMISSION_ERROR(1008, "无权限操作"),

    LOGIN_ERROR(2000, "登陆失败"),
    LOGIN_OVERDUE(2001, "会话已过期，请重新登陆"),
    NOT_LOGIN(2002,"用户未登录"),
    OUT_ERROR(2003,"登出失败"),
    NOT_PERMISSION(2004,"权限不足"),
    

    USERNAME_IS_EXISTED(-100, "该用户名已存在"),
    USER_NOT_EXISTED(-101, "该用户不存在"),
    OLD_PASSWORD_ERROR(-102, "旧密码不正确");

    private final int code;
    private final String message;
}
