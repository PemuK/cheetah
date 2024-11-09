package com.ujnbox.cheetah.service.impl;

import com.ujnbox.cheetah.dao.MaintTypeDao;
import com.ujnbox.cheetah.model.dox.MaintTypeDo;
import com.ujnbox.cheetah.service.MaintTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class MaintTypeServiceImpl implements MaintTypeService {

    @Autowired
    MaintTypeDao maintTypeDao;

    @Override
    public List<MaintTypeDo> list(Integer status) {
        return maintTypeDao.listByStatus(status);
    }

    @Override
    public boolean update(Integer id,String typeName,Integer amount) {
        MaintTypeDo maintTypeDo = MaintTypeDo.builder()
                .id(id)
                .typeName(typeName)
                .workAmount(amount)
                .build();
        return maintTypeDao.updateById(maintTypeDo)>0;
    }

    @Override
    public boolean add( String typeName,Integer amount) {
        MaintTypeDo maintTypeDo = MaintTypeDo.builder()
                .typeName(typeName)
                .workAmount(amount)
                .build();
        return maintTypeDao.insert(maintTypeDo)>0;
    }

    @Override
    public boolean updateStatusById(Integer id, Integer status) {
        return maintTypeDao.updateStatusById(status, id)>0;
    }

}
