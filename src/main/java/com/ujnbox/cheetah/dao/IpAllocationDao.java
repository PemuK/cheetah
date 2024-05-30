package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.IpAllocationDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IpAllocationDao {
    @Insert("""
            INSERT INTO ip_allocation(network_info_id,ip_address)
            VALUES(#{networkInfoId},#{ipAddress})
            """)
    int insert(@Param("networkInfoId")Integer networkInfoId,
               @Param("ipAddress")String ipAddress);

    @Update("""
            UPDATE ip_allocation
            SET client_name=#{clientName}
            WHERE id=#{id}
            """)
    int updateClientNameById(@Param("clientName")String cientName,
                             @Param("id")Integer id);

    @Update("""
            UPDATE ip_allocation
            SET phone_number=#{phoneNumber}
            WHERE id=#{id}
            """)
    int updatePhoneNumberById(@Param("phoneNumber")String phoneNumber,
                              @Param("id")Integer id);

    @Update("""
            UPDATE ip_allocation
            SET start_time=#{startTime}
            WHERE id=#{id}
            """)
    int updateStartTimeById(@Param("startTime")String startTime,
                            @Param("id")Integer id);

    @Update("""
            UPDATE ip_allocation
            SET end_time=#{endTime}
            WHERE id=#{id}
            """)
    int updateEndTimeById(@Param("endTime")String endTime,
                          @Param("id")Integer id);

    @Select("""
            SELECT id,network_info_id,ip_address,client_name,phone_number,create_time,update_time,start_time,end_time,status
            FROM ip_allocation
            WHERE network_info_id=#{networkInfoId}
            """)
    List<IpAllocationDo>getByNetworkInfoId(@Param("networkInfoId")String networkInfoId);


}
