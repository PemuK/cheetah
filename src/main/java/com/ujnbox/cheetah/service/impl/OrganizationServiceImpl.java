// OrganizationServiceImpl.java
package com.ujnbox.cheetah.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.dao.OrganizationDao;
import com.ujnbox.cheetah.model.dox.OrganizationDo;
import com.ujnbox.cheetah.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织服务接口的实现类。
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;
    private List<OrganizationDo> list;

    /**
     * 构造方法，通过依赖注入注入OrganizationDao。
     *
     * @param organizationDao 组织DAO接口
     */
    @Autowired
    public OrganizationServiceImpl(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addOrganization(OrganizationDo organizationDo) {
        return organizationDao.insert(organizationDo) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateOrganizationNameById(String organizationName, Integer id) {
        return organizationDao.updateOrganizationNameById(organizationName, id) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateStatusById(Integer status, Integer id) {
        return organizationDao.updateStatusById(status, id) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationDo getByIdAndStatus(Integer id, Integer status) {
        return organizationDao.getByIdAndStatus(id, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseMsg<?> pageByStatus(Integer pageNum, Integer pageSize, Integer status) {
        // 启动分页
        String orderBy = "id desc"; // 按照排序字段倒序排序
        PageHelper.startPage(pageNum, pageSize, orderBy);
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        List<OrganizationDo> list = organizationDao.listByStatus(params);
        PageInfo<OrganizationDo> pageInfo = new PageInfo<>(list);
        return ResponseMsg.success(pageInfo);
    }

    @Override
    public ResponseMsg<?> listByStatus(Integer status) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        List<OrganizationDo> list = organizationDao.listByStatus(params);
        return ResponseMsg.success(list);
    }

    @Override
    public ResponseMsg<?> listByName(Integer pageNum, Integer pageSize, String name, Integer status) {
        String orderBy = "id desc"; // 按照排序字段倒序排序
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<OrganizationDo> list = organizationDao.listByName(name, status);
        PageInfo<OrganizationDo> pageInfo = new PageInfo<>(list);
        return ResponseMsg.success(pageInfo);
    }
}
