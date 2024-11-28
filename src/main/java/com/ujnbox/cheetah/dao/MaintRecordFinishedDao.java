package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.vo.MaintRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface MaintRecordFinishedDao {

    @Select("""
            SELECT id, client_id, adder_id,adder_name, reporter_id,reporter_name, maint_type,maint_type_name, maint_description, location_description, status, create_time, update_time, state,finish_time,note,completer,client_name,phone_number,unit,room,building_id,building_name
            FROM maint_record_finished
            WHERE  state = #{state}
            """)
    List<MaintRecordVo> listByState(Map params);

    @Select("""
            SELECT id, client_id, adder_id, adder_name, reporter_id, reporter_name, 
                   maint_type, maint_type_name,work_amount, maint_description, location_description, 
                   status, create_time, update_time, state, finish_time, note, completer, 
                   client_name, phone_number, unit, room, building_id, building_name
            FROM maint_record_finished
            WHERE state = #{state} 
              AND finish_time > #{start_time} 
              AND finish_time < #{end_time} 
              AND completer LIKE CONCAT('%', #{userId}, '%')
            """)
    List<MaintRecordVo> listByStateAndIdAndTime(@Param("state") Integer state,
                                                @Param("start_time") LocalDateTime startTime,
                                                @Param("end_time") LocalDateTime endTime,
                                                @Param("userId") Integer userId);

    @Select("""
            SELECT id, client_id, adder_id, adder_name, reporter_id, reporter_name, 
                   maint_type, maint_type_name,work_amount, maint_description, location_description, 
                   status, create_time, update_time, state, finish_time, note, completer, 
                   client_name, phone_number, unit, room, building_id, building_name, work_amount
            FROM maint_record_finished
            WHERE state = #{state} 
              AND completer LIKE CONCAT('%', #{userId}, '%')
            ORDER BY finish_time DESC
            """)
    List<MaintRecordVo> listByStateAndId(@Param("state") Integer state,
                                         @Param("userId") Integer userId);


    @Select("""
            SELECT id, client_id, adder_id, adder_name, reporter_id, reporter_name, 
                   maint_type, maint_type_name, maint_description, location_description, 
                   status, create_time, update_time, state, finish_time, note, completer, 
                   client_name, phone_number, unit, room, building_id, building_name
            FROM maint_record_finished
            WHERE state = #{state} 
              AND finish_time > #{start_time} 
              AND finish_time < #{end_time} 
            """)
    List<MaintRecordVo> listByStateAndTimeRange(@Param("state") Integer state,
                                                @Param("start_time") LocalDateTime startTime,
                                                @Param("end_time") LocalDateTime endTime);

    @Select("""
            SELECT  id, client_id, client_name, maint_type, maint_type_name, maint_description, location_description, 
                    status, create_time, finish_time, note, client_name, phone_number, unit, room, building_id, building_name
            FROM maint_record_finished
            WHERE building_id = #{buildingId} 
              AND unit LIKE CONCAT('%', #{unit}, '%')  
              AND room LIKE CONCAT('%', #{room}, '%')
              AND state=#{state}
            ORDER BY finish_time DESC
            """)
    List<MaintRecordVo> listByLocation(@Param("buildingId") Integer buildingId,
                                       @Param("unit") String unit,
                                       @Param("room") String room,
                                       @Param("state") Integer state);


    @Select("""
            SELECT  id, client_id, client_name,maint_type, maint_type_name, maint_description, location_description, 
                    status, create_time, finish_time, note,client_name, phone_number, unit, room, building_id, building_name
            FROM maint_record_finished
            WHERE client_name LIKE  CONCAT('%',#{clientName},'%') AND state=#{state}
            ORDER BY finish_time DESC
            """)
    List<MaintRecordVo> listByClientName(@Param("clientName") String clientName,
                                         @Param("state") Integer state);

    @Select("""
            SELECT  id, client_id, client_name,maint_type, maint_type_name, maint_description, location_description, 
                    status, create_time, finish_time, note,client_name, phone_number, unit, room, building_id, building_name
            FROM maint_record_finished
            WHERE reporter_id=#{reporterId} state=#{state}
            ORDER BY finish_time DESC
            """)
    List<MaintRecordVo> listByReporter(@Param("reporter_id") String reporterId,
                                       @Param("state") Integer state);

    @Select("""
            SELECT id, client_id, client_name, maint_type, maint_type_name, maint_description, location_description, 
                   status, create_time, finish_time, note, client_name, phone_number, unit, room, building_id, building_name
            FROM maint_record_finished
            WHERE phone_number LIKE CONCAT(#{phoneNumber}, '%') AND state = #{state}  
            ORDER BY finish_time DESC
            """)
    List<MaintRecordVo> listPhoneNumber(@Param("phoneNumber") String phoneNumber,
                                        @Param("state") Integer state);


    @Select("""
            SELECT id, client_id, adder_id, adder_name, reporter_id, reporter_name, 
                       maint_type, maint_type_name, maint_description, location_description, 
                       status, create_time, update_time, state, finish_time, note, completer, 
                       client_name, phone_number, unit, room, building_id, building_name
            FROM maint_record_finished
            WHERE state = #{state}
              AND CONCAT(building_name, unit, room) LIKE CONCAT('%', #{searchTerm}, '%')
            ORDER BY finish_time DESC
            """)
    List<MaintRecordVo> listByBuildingUnitRoom(@Param("searchTerm") String searchTerm,
                                               @Param("state") Integer state);

    @Select("""
            SELECT id, client_id, adder_id, adder_name, reporter_id, reporter_name, 
                       maint_type, maint_type_name, maint_description, location_description, 
                       status, create_time, update_time, state, finish_time, note, completer, 
                       client_name, phone_number, unit, room, building_id, building_name
            FROM maint_record_finished
            WHERE  maint_type=#{maintType} AND state = #{state}
            """)
    List<MaintRecordVo> listByMaintType(@Param("maintType") Integer maintType,
                                        @Param("state") Integer state);

    @Select("""
            SELECT id, client_id, adder_id, adder_name, reporter_id, reporter_name, 
                       maint_type, maint_type_name, maint_description, location_description, 
                       status, create_time, update_time, state, finish_time, note, completer, 
                       client_name, phone_number, unit, room, building_id, building_name
            FROM maint_record_finished
            WHERE state = #{state}
              AND maint_description  LIKE CONCAT('%', #{description}, '%')
            ORDER BY finish_time DESC
            """)
    List<MaintRecordVo> listByMaintDescription(@Param("description") String description,
                                               @Param("state") Integer state);
}
