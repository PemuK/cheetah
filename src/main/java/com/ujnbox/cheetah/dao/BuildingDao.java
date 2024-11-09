package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.BuildingDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuildingDao {
    @Insert("""
            INSERT INTO building(building_name,type,campus)
            VALUES (#{buildingName},#{type},#{campus})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BuildingDo buildingDo);

    @Update("""
            UPDATE building
            SET type=#{type}
            WHERE id=#{id}
            """)
    int updateTypeById(@Param("type") Integer type,
                       @Param("id") Integer id);

    @Update("""
            UPDATE building
            SET campus=#{campus}
            WHERE id=#{id}
            """)
    int updateCampusById(@Param("campus") Integer campus,
                         @Param("id") Integer id);

    @Update("""
            UPDATE building
            SET campus=#{campus},type=#{type},building_name=#{buildingName}
            WHERE id=#{id} 
            """)
    int updateById(BuildingDo buildingDo);

    @Update("""
            UPDATE building
            SET status=#{status}
            WHERE id=#{id} 
            """)
    int updateStatusById(@Param("id") Integer id,
                         @Param("status") Integer status);

    @Select("""
            SELECT id,building_name,type,campus,create_time,update_time,status FROM building
            WHERE building_name LIKE CONCAT('%',#{term}, '%') AND status=#{status}
            """)
    List<BuildingDo> listByBuildingNameAndStatus(@Param("term") String term,
                                                 @Param("status") Integer status);

    @Select("""
            SELECT id,building_name,type,campus,create_time,update_time,status FROM building
            WHERE id=#{id} AND status=#{status}
            """)
    BuildingDo getByIdAndStatus(@Param("id") Integer id,
                                @Param("status") Integer status);

    @Select("""
            SELECT id,building_name,type,campus,create_time,update_time,status FROM building
            WHERE status=#{status}
            """)
    List<BuildingDo> listByStatus(@Param("status") Integer status);

    @Select("""
        SELECT id, building_name, type, campus, create_time, update_time, status
        FROM building
        WHERE building_name LIKE CONCAT('%', #{buildingName}, '%') AND status = #{status}
        """)
    List<BuildingDo> listByName(@Param("buildingName") String buildingName,
                                @Param("status") Integer status);

}
