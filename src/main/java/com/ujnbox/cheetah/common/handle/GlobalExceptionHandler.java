package com.ujnbox.cheetah.common.handle;

import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.MsuException;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseMsg<Void> exceptionHandler(HttpServletRequest request, RuntimeException ex){
        if(!ex.getMessage().equals("用户未登录")
                && !ex.getMessage().equals("验证失败")){
            ex.printStackTrace();
        }

        if(ex instanceof MsuException ujnboxException){
            return ResponseMsg.error(ujnboxException);
        }
        return ResponseMsg.error(ErrorCodeEnum.SYSTEM_ERROR);
    }
}
