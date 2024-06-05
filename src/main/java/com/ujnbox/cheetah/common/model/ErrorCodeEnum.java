package com.ujnbox.cheetah.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {
    SYSTEM_ERROR(-999, "系统错误"),

    // 1000-2000 范围内的维护记录相关错误代码
    MAINT_RECORD_INSERT_FAILED(1001, "维护记录插入失败"),
    MAINT_RECORD_QUERY_FAILED(1002, "查询维护记录失败");

    private final int code;
    private final String message;
}
