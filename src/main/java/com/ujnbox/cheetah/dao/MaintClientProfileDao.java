package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.MaintClientProfileDo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MaintClientProfileDao {
    @Insert("""
            INSERT INTO maint_client_profile(client_name, phone_number, unit, room, building_id)
            VALUES(#{clientName}, #{phoneNumber}, #{room}, #{buildingId})
            """)
    int insert(@Param("clientName") String clientName,
               @Param("phoneNumber") String phoneNumber,
               @Param("room") String room,
               @Param("buildingId") Integer buildingId);

    @Update("""
            UPDATE maint_client_profile
            SET client_name=#{clientName}
            WHERE id=#{id}
            """)
    int updateClientNameById(@Param("clientName") String clientName,
                             @Param("id") Integer id);

    @Update("""
            UPDATE maint_client_profile
            SET unit=#{unit}
            WHERE id=#{id}
            """)
    int updateUnitById(@Param("unit") String unit,
                       @Param("id") Integer id);

    @Update("""
            UPDATE maint_client_profile
            SET room=#{room}
            WHERE id=#{id}
            """)
    int updateRoomById(@Param("room") String room,
                       @Param("id") Integer id);

    @Update("""
            UPDATE maint_client_profile
            SET building_id=#{buildingId}
            WHERE id=#{id}
            """)
    int updateBuildingIdById(@Param("buildingId") Integer buildingId,
                             @Param("id") Integer id);

    @Update("""
            UPDATE maint_client_profile
            SET status=#{status}
            WHERE id=#{id}
            """)
    int updateStatusById(@Param("status") Integer status,
                         @Param("id") Integer id);

    @Select("""
            SELECT id, client_name, phone_number, unit, room, building_id, create_time, update_time, status
            FROM maint_client_profile
            WHERE id=#{id} AND status=#{status}
            """)
    MaintClientProfileDo getByIdAndStatus(@Param("id") Integer id,
                                          @Param("status") Integer status);
}
