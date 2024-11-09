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
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaintTypeDo maintTypeDo);

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
    int updateWorkAmountById(@Param("workAmount") Integer workAmount,
                             @Param("id") Integer id);


    @Update("""
            UPDATE maint_type
            SET work_amount=#{workAmount},type_name=#{typeName}
            WHERE id=#{id}
            """)
    int updateById(MaintTypeDo maintTypeDo);

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
             WHERE status=#{status}
            """)
    List<MaintTypeDo> listByStatus(@Param("status") Integer status);

    @Select("""
            SELECT id,type_name,work_amount,create_time,update_time,status
            FROM maint_type
             WHERE status=#{status} AND id=#{id}
            """)
    MaintTypeDo getByIdAndStatus(@Param("id") Integer id,
                                 @Param("status") Integer status);

    @Select("""
            SELECT id,type_name,work_amount,create_time,update_time,status
            FROM maint_type 
            """)
    List<MaintTypeDo> list();
}
