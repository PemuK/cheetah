package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.ParticipateMaintUserDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ParticipateMaintUserDao {
    @Insert("""
            INSERT INTO participate_maint_user (maintenance_record_id, user_id)
            VALUES (#{maintenanceRecordId}, #{userId})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ParticipateMaintUserDo participateMaintUserDo);

    @Update("""
            UPDATE participate_maint_user
            SET status = #{status}
            WHERE id = #{id}
            """)
    int updateStatusById(@Param("status") Integer status,
                         @Param("id") Integer id);

    @Select("""
            SELECT id, maintenance_record_id, user_id, create_time, update_time, status
            FROM participate_maint_user
            WHERE id = #{id} AND status = #{status}
            """)
    ParticipateMaintUserDo getByIdAndStatus(@Param("id") Integer id,
                                            @Param("status") Integer status);

    @Select("""
            SELECT id, maintenance_record_id, user_id, create_time, update_time, status
            FROM participate_maint_user
            WHERE status = #{status}
            """)
    List<ParticipateMaintUserDo> listByStatus(@Param("status") Integer status);
}
