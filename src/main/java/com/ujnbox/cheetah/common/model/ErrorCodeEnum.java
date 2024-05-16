package com.ujnbox.cheetah.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {
    SYSTEM_ERROR(-999, "系统错误");
    private final int code;
    private final String message;
}
