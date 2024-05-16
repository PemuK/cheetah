package com.ujnbox.cheetah.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMsg<T> {
    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static ResponseMsg<Void> success(){
        ResponseMsg<Void> result = new ResponseMsg<>();
        result.code = 200;
        result.message = "success";
        return result;
    }

    public static <T> ResponseMsg<T> success(T data){
        ResponseMsg<T> result = new ResponseMsg<>();
        result.code = 200;
        result.message = "success";
        result.data = data;
        return result;
    }

    public static ResponseMsg<Void> error(int code, String message){
        ResponseMsg<Void> result = new ResponseMsg<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static ResponseMsg<Void> error(ErrorCodeEnum errorCodeEnum){
        ResponseMsg<Void> result = new ResponseMsg<>();
        result.code = errorCodeEnum.getCode();
        result.message = errorCodeEnum.getMessage();
        return result;
    }

    public static ResponseMsg<Void> error(MsuException msuException){
        ResponseMsg<Void> result = new ResponseMsg<>();
        result.code = msuException.getCode();
        result.message = msuException.getMessage();
        return result;
    }
}
