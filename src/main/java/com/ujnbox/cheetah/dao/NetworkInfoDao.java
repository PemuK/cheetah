package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.NetworkInfoDo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface NetworkInfoDao {
    @Insert("""
            INSERT INTO network_info(organization_id,gateway_ip,subnet_mask)
            VALUES (#{organizationId},#{gatewayIp},#{subnetMask})
            """)
    int insert(@Param("organizationId")Integer organizationId,
               @Param("gatewayIp")String gatewayIp,
               @Param("subnetMask")String subnetMask);

    @Update("""
            UPDATE network_info
            SET description=#{description}
            WHERE id=#{id}
            """)
    int updateDescriptionById(@Param("description")String description,
                              @Param("id")Integer id);

    @Update("""
            UPDATE network_info
            SET start_time=#{startTime}
            WHERE id=#{id}
            """)
    int updateStartTimeById(@Param("startTime") LocalDateTime startTime,
                              @Param("id")Integer id);

    @Update("""
            UPDATE network_info
            SET end_time=#{endTime}
            WHERE id=#{id}
            """)
    int updateEndTimeById(@Param("endTime") LocalDateTime endTime,
                            @Param("id")Integer id);

    @Update("""
            UPDATE network_info
            SET status=#{status}
            WHERE id=#{id}
            """)
    int updateStatusById(@Param("status") Integer status,
                            @Param("id")Integer id);

    @Select("""
            SELECT id,organization_id,gateway_ip,subnet_mask,description,start_time,end_time,status,create_time,update_time
            FROM network_info
            """)
    List<NetworkInfoDo> listNetworkInfo();

    @Select("""
            SELECT id,organization_id,gateway_ip,subnet_mask,description,start_time,end_time,status,create_time,update_time
            FROM network_info
            WHERE organization_id=#{organizationId}
            """)
    List<NetworkInfoDo> listNetworkInfoByOrganizationId(@Param("organizationId")Integer organizationId);
}
