package com.ujnbox.cheetah.common.model;

import lombok.Getter;

@Getter
public class MsuException extends RuntimeException {

    private int code = -999;
    private String msg = "服务器错误";


    public MsuException() {

    }


    public MsuException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public MsuException(ErrorCodeEnum errorCodeEnum){
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMessage();
    }


}
