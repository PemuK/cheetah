package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.WorkAmountRecordDo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface WorkAmountRecordDao {
    @Insert("""
            INSERT INTO work_amount_record(user_id, week_amount, month_amount, quarter_amount, total_amount)
            VALUES (#{userId}, #{weekAmount}, #{monthAmount}, #{quarterAmount}, #{totalAmount})
            """)
    int insert(@Param("userId") Integer userId,
               @Param("weekAmount") Integer weekAmount,
               @Param("monthAmount") Integer monthAmount,
               @Param("quarterAmount") Integer quarterAmount,
               @Param("totalAmount") Integer totalAmount);

    @Update("""
            UPDATE work_amount_record
            SET status = #{status}
            WHERE id = #{id}
            """)
    int updateStatusById(@Param("status") Integer status,
                         @Param("id") Integer id);

    @Select("""
            SELECT id,user_id, week_amount, month_amount, quarter_amount, total_amount, create_time, update_time, status
            FROM work_amount_record
            WHERE user_id = #{userId}
            """)
    WorkAmountRecordDo getByUserId(@Param("userId") Integer userId);

    @Select("""
            SELECT id,user_id, week_amount, month_amount, quarter_amount, total_amount, create_time, update_time, status
            FROM work_amount_record
            WHERE create_time = #{time} AND status=#{status}
            """)
    List<WorkAmountRecordDo> listByTimeAndStatus(@Param("time") LocalDateTime time,
                                                 @Param("status") Integer status);

    @Select("""
            SELECT id,user_id, week_amount, month_amount, quarter_amount, total_amount, create_time, update_time, status
            FROM work_amount_record
            WHERE status=#{status}
            """)
    List<WorkAmountRecordDo> listByStatus(@Param("status") Integer status);
}
