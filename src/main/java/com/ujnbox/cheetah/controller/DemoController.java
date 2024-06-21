package com.ujnbox.cheetah.controller;

import com.ujnbox.cheetah.common.model.ErrorCodeEnum;
import com.ujnbox.cheetah.common.model.MsuException;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {
    @Autowired
    private IDemoService demoService;

    @PostMapping(value = "/add")
    public ResponseMsg<?> add(@RequestParam(value = "name")String name,
                              @RequestParam(value = "address", required = false, defaultValue = "")String address,
                              @RequestParam(value = "age")Integer age){
        if(demoService.add(name, address, age)){
            return ResponseMsg.success();
        }

//        return ResponseMsg.error(ErrorCodeEnum.SYSTEM_ERROR);
        throw new MsuException(ErrorCodeEnum.SYSTEM_ERROR);
    }

}
