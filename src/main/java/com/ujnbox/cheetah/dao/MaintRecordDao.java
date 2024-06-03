package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.MaintRecordDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MaintRecordDao {
    @Insert("""
            INSERT INTO maint_record(client_id, adder_id, maint_type, maint_description,location_description)
            VALUES(#{clientId}, #{adderId}, #{maintType}, #{maintDescription},#{locationDescription})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaintRecordDo maintRecordDo);

    @Update("""
            UPDATE maint_record
            SET maint_description=#{maintDescription}
            WHERE id=#{id}
            """)
    int updateMaintDescriptionById(@Param("maintDescription") String maintDescription,
                                   @Param("id") Integer id);

    @Update("""
            UPDATE maint_record
            SET location_description=#{locationDescription}
            WHERE id=#{id}
            """)
    int updateLocationDescriptionById(@Param("locationDescription") String locationDescription,
                                      @Param("id") Integer id);

    @Update("""
            UPDATE maint_record
            SET maint_type=#{maintType}
            WHERE id=#{id}
            """)
    int updateTypeById(@Param("maintType") Integer maintType,
                       @Param("id") Integer id);

    @Update("""
            UPDATE maint_record
            SET status=#{status}
            WHERE id=#{id}
            """)
    int updateStatusById(@Param("status") Integer status,
                         @Param("id") Integer id);

    @Update("""
            UPDATE maint_record
            SET state=#{state}
            WHERE id=#{id}
            """)
    int updateStateById(@Param("state") Integer state,
                        @Param("id") Integer id);

    @Update("""
            UPDATE maint_record
            SET reporter_id=#{reporterId}
            WHERE id=#{id}
            """)
    int updateReporterIdById(@Param("reporterId") Integer reporterId,
                             @Param("id") Integer id);

    @Select("""
            SELECT id, client_id, adder_id, reporter_id, maint_type, maint_description, location_description, status, create_time, update_time, finish_time, state
            FROM maint_record
            WHERE status=#{status} AND state=#{state}
            """)
    List<MaintRecordDo> listByStatusAndState(@Param("status") Integer status,
                                             @Param("state") Integer state);

    @Select("""
            SELECT id, client_id, adder_id, reporter_id, maint_type, maint_description, location_description, status, create_time, update_time, finish_time, state
            FROM maint_record 
            WHERE state=#{state}
            """)
    List<MaintRecordDo> listByState(@Param("state") Integer state);
}
