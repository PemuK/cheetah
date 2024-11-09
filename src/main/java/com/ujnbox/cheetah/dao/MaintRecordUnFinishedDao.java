package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.vo.MaintRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MaintRecordUnFinishedDao {
    @Select("""
            SELECT id, client_id, adder_id,adder_name, reporter_id,reporter_name, maint_description, location_description, status, create_time, update_time, state,finish_time,note,completer,client_name,phone_number,unit,room,building_id,building_name
            FROM maint_record_unfinished
            WHERE state=#{state}
            """)
    List<MaintRecordVo> listByState(@Param("state") Integer state);
}
