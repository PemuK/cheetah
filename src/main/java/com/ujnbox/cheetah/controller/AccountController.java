package com.ujnbox.cheetah.controller;

import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.service.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    public ResponseMsg<?> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        return accountService.login(username, password, response);
    }
}
