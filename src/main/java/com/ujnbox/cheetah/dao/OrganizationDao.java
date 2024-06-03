package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.OrganizationDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrganizationDao {

    @Insert("""
            INSERT INTO organization (organization_name)
            VALUES (#{organizationName})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OrganizationDo organizationDo);

    @Update("""
            UPDATE organization
            SET organization_name = #{organizationName}
            WHERE id = #{id}
            """)
    int updateOrganizationNameById(@Param("organizationName") String organizationName,
                                   @Param("id") Integer id);

    @Update("""
            UPDATE organization
            SET status = #{status}
            WHERE id = #{id}
            """)
    int updateStatusById(@Param("status") Integer status,
                         @Param("id") Integer id);

    @Select("""
            SELECT id, organization_name, create_time, update_time, status
            FROM organization
            WHERE id = #{id} AND status = #{status}
            """)
    OrganizationDo getByIdAndStatus(@Param("id") Integer id,
                           @Param("status") Integer status);

    @Select("""
            SELECT id, organization_name, create_time, update_time, status
            FROM organization 
            WHERE status = #{status}
            """)
    List<OrganizationDo> listByStatus(@Param("status") Integer status);
}
