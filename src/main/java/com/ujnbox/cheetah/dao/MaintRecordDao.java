package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.MaintRecordDo;
import com.ujnbox.cheetah.model.vo.MaintRecordVo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface MaintRecordDao {

    // INSERT operations
    @Insert("""
            INSERT INTO maint_record(client_id, adder_id, maint_description,location_description)
            VALUES(#{clientId}, #{adderId}, #{maintDescription},#{locationDescription})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaintRecordDo maintRecordDo);

    // UPDATE operations
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

    @Update("""
            UPDATE maint_record
            SET note=#{note}
            WHERE id=#{id}
            """)
    int updateNoteById(@Param("note") String note,
                       @Param("id") Integer id);

    @Update("""
            UPDATE maint_record
            SET completer=#{completer}
            WHERE id=#{id}
            """)
    int updateCompleterById(@Param("completer") String completer,
                            @Param("id") Integer id);

    @Update("""
            UPDATE maint_record
            SET finish_time=#{finishTime}
            WHERE id=#{id}
            """)
    int updateFinishTimeById(@Param("finishTime") LocalDateTime finishTime,
                             @Param("id") Integer id);

    @Update("""
            UPDATE maint_record
            SET 
                maint_description = #{maintDescription}, 
                location_description = #{locationDescription}
            WHERE
                id = #{id}
            """)
    int updateDescById(
            @Param("maintDescription") String maintDescription,
            @Param("locationDescription") String locationDescription,
            @Param("id") Integer id);

    @Update("""
            UPDATE maint_record
            SET maint_type=#{maintType}
            WHERE id=#{id}
            """)
    int updateMaintTypeById(@Param("maintType") Integer maintType,
                            @Param("id") Integer id);

    // SELECT operations
    @Select("""
            SELECT id, client_id, adder_id, reporter_id, maint_type, maint_description, location_description, status, create_time, update_time, state,finish_time,note,completer
            FROM maint_record
            WHERE status=#{status} AND state=#{state}
            """)
    List<MaintRecordDo> listByStatusAndState(@Param("status") Integer status,
                                             @Param("state") Integer state);


    @Select("""
            SELECT id, client_id, adder_id, reporter_id, maint_type, maint_description, location_description, status, create_time, update_time, state,finish_time,note,completer
            FROM maint_record 
            WHERE state=#{state}
            """)
    List<MaintRecordDo> listByState(@Param("state") Integer state);

    @Select("""
            SELECT id, client_id, adder_id, reporter_id, maint_type, maint_description, location_description, status, create_time, update_time, state,finish_time,note,completer
            FROM maint_record
            WHERE id=#{id}
                    """)
    MaintRecordVo getById(Integer id);
}
