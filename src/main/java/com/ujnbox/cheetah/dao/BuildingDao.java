package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.BuildingDo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BuildingDao {
    @Insert("""
            INSERT INTO building
            VALUES (#{buildingName},#{type},#{campus})
            """)
    int insert(@Param("buildingName")String buildingName,
               @Param("type")Integer type,
               @Param("campus")Integer campus);

    @Select("""
            SELECT * FROM buidling
            WHERE building_name LIKE CONCAT('%',#{term}, '%')
        """)
    List<BuildingDo> listByBuildingName(@Param("term")String term);

    @Select("""
            SELECT * FROM Building
            WHERE id=#{id}
            """)
    BuildingDo getById(@Param("id")String id);
}
