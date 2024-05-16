package com.ujnbox.cheetah.service.impl;

import com.ujnbox.cheetah.dao.DemoDao;
import com.ujnbox.cheetah.model.dox.DemoDo;
import com.ujnbox.cheetah.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private DemoDao demoDao;

    @Override
    public boolean add(String name, String address, Integer age) {
        DemoDo demoDo = DemoDo.builder()
                .name(name)
                .address(address)
                .age(age)
                .build();
        return demoDao.insert(demoDo) > 0;
    }
}
