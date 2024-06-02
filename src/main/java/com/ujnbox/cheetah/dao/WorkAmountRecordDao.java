package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.WorkAmountRecordDo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface WorkAmountRecordDao {
    @Insert("""
            INSERT INTO work_amount_record(user_id,amount)
            VALUES (#{userId}, #{amount})
            """)
    int insert(@Param("userId") Integer userId,
               @Param("amount") Integer weekAmount);

    @Update("""
            UPDATE work_amount_record
            SET status = #{status}
            WHERE id = #{id}
            """)
    int updateStatusById(@Param("status") Integer status,
                         @Param("id") Integer id);

    //返回当前维护员的所有维护量记录
    @Select("""
            SELECT id,user_id, amount, create_time, update_time, status
            FROM work_amount_record
            WHERE user_id = #{userId} AND status=#{status}
            """)
    List<WorkAmountRecordDo> listByUserId(@Param("userId") Integer userId,
                                          @Param("status") Integer status);

    @Select("""
            SELECT id,user_id, amount, create_time, update_time, status
            FROM work_amount_record
            WHERE status=#{status}
            """)
    List<WorkAmountRecordDo> listByStatus(@Param("status") Integer status);

    //对应月返回对应维护员的维护量记录（但输入时间类型为LocalDateTime）
    @Select("""
            SELECT id, user_id, amount, create_time, update_time, status
            FROM work_amount_record
            WHERE YEAR(create_time) = YEAR(#{dateTime}) 
                AND MONTH(create_time) = MONTH(#{dateTime}) 
                AND status = #{status} 
                AND user_id = #{userId}
            """)
    List<WorkAmountRecordDo> listByUserIdAndMonthAndStatus(@Param("date") LocalDateTime date,
                                                           @Param("status") Integer status);
}
