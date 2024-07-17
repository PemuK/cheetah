package com.ujnbox.cheetah.service;

import com.ujnbox.cheetah.common.model.ResponseMsg;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountService {
    ResponseMsg<?> login(String username, String password, HttpServletResponse response);
}
