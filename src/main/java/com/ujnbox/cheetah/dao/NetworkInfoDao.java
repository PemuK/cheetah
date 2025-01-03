package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.NetworkInfoDo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface NetworkInfoDao {

    @Insert("""
            INSERT INTO network_info (organization_id, gateway_ip, subnet_mask)
            VALUES (#{organizationId}, #{gatewayIp}, #{subnetMask})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(NetworkInfoDo networkInfoDo);

    @Update("""
            UPDATE network_info
            SET description = #{description}
            WHERE id = #{id}
            """)
    int updateDescriptionById(@Param("description") String description,
                              @Param("id") Integer id);

    @Update("""
            UPDATE network_info
            SET start_time = #{startTime}
            WHERE id = #{id}
            """)
    int updateStartTimeById(@Param("startTime") LocalDateTime startTime,
                            @Param("id") Integer id);

    @Update("""
            UPDATE network_info
            SET end_time = #{endTime}
            WHERE id = #{id}
            """)
    int updateEndTimeById(@Param("endTime") LocalDateTime endTime,
                          @Param("id") Integer id);

    @Update("""
            UPDATE network_info
            SET status = #{status}
            WHERE id = #{id}
            """)
    int updateStatusById(@Param("status") Integer status,
                         @Param("id") Integer id);

    @Select("""
            SELECT id, organization_id, gateway_ip, subnet_mask, description, start_time, end_time, status, create_time, update_time
            FROM network_info 
            WHERE status = #{status}
            """)
    List<NetworkInfoDo> listByStatus(@Param("status") Integer status);

    @Select("""
            SELECT id, organization_id, gateway_ip, subnet_mask, description, start_time, end_time, status, create_time, update_time
            FROM network_info
            WHERE organization_id = #{organizationId} AND status = #{status}
            """)
    List<NetworkInfoDo> listByOrganizationIdAndStatus(@Param("organizationId") Integer organizationId,
                                                                 @Param("status") Integer status);
}
