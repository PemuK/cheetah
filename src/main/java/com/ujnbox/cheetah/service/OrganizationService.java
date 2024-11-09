// OrganizationService.java
package com.ujnbox.cheetah.service;

import com.ujnbox.cheetah.common.model.ResponseMsg;
import com.ujnbox.cheetah.model.dox.OrganizationDo;

import java.util.List;

/**
 * 组织服务接口，定义与组织相关的业务操作。
 */
public interface OrganizationService {

    /**
     * 添加组织。
     *
     * @param organizationDo 组织实体对象
     * @return 插入的组织ID
     */
    boolean addOrganization(OrganizationDo organizationDo);

    /**
     * 根据ID更新组织名称。
     *
     * @param organizationName 新的组织名称
     * @param id               组织ID
     * @return 更新的行数
     */
    boolean updateOrganizationNameById(String organizationName, Integer id);

    /**
     * 根据ID更新组织状态。
     *
     * @param status 新的状态值
     * @param id     组织ID
     * @return 更新的行数
     */
    boolean updateStatusById(Integer status, Integer id);

    /**
     * 根据ID和状态获取组织信息。
     *
     * @param id     组织ID
     * @param status 状态值
     * @return 组织实体对象
     */
    OrganizationDo getByIdAndStatus(Integer id, Integer status);

    /**
     * 根据状态列出所有组织。
     *
     * @param status 状态值
     * @return 组织实体对象列表
     */
    ResponseMsg<?> pageByStatus(Integer pageNum, Integer pageSize, Integer status);

    ResponseMsg<?> listByStatus(Integer status);

    ResponseMsg<?> listByName( Integer pageNum,Integer pageSize, String name,Integer status);
}
