package com.ujnbox.cheetah.service;

import com.ujnbox.cheetah.model.dox.MaintTypeDo;

import java.util.List;

public interface MaintTypeService {
    List<MaintTypeDo> list(Integer status);
    boolean update(Integer id,String typeName,Integer amount);
     boolean add(String typeName,Integer amount);
    boolean updateStatusById(Integer id,Integer status);
}
