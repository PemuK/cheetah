package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.MaintTypeDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MaintTypeDao {
    @Insert("""
            INSERT INTO maint_type(type_name,work_amount)
            VALUES (#{typeName},#{workAmount})
            """)
    int insert(@Param("typeName") String typeName,
               @Param("workAmount") Integer workAmount);

    @Update("""
            UPDATE maint_type
            SET type_name=#{typeName}
            WHERE id=#{id}
            """)
    int updateTypeNameById(@Param("typeName") String typeName,
                           @Param("id") Integer id);

    @Update("""
            UPDATE maint_type
            SET work_amount=#{workAmount}
            WHERE id=#{id}
            """)
    int updateWorkAmountById(@Param("workAmount") String workAmount,
                             @Param("id") Integer id);

    @Update("""
            UPDATE maint_type
            SET status=#{status}
            WHERE id=#{id}
            """)
    int updateStatusById(@Param("status") Integer status,
                         @Param("id") Integer id);

    @Select("""
            SELECT id,type_name,work_amount,create_time,update_time,status
            FROM maint_type 
             WHERE status=1
            """)
    List<MaintTypeDo> getAll();
}
