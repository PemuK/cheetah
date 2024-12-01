package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.UserDo;
import com.ujnbox.cheetah.model.vo.UserVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Insert("""
            INSERT INTO `user`(username,name, password,phone_number,start_year, organization_id)
            VALUES (#{username}, #{name},#{password},#{phoneNumber},#{startYear}, #{organizationId})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserDo userDo);

    @Update("""
            UPDATE `user`
            SET username=#{username}
            WHERE id=#{id}
            """)
    int updateUsernameById(@Param("username") String username, @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET password = #{newPassword}
            WHERE id = #{id} AND password = #{oldPassword}
            """)
    int updatePasswordById(@Param("oldPassword") String oldPassword,
                           @Param("newPassword") String newPassword,
                           @Param("id") Integer id);


    @Update("""
            UPDATE `user`
            SET name=#{name}
            WHERE id=#{id}
            """)
    int updateNameById(@Param("name") String name, @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET phone_number=#{phoneNumber}
            WHERE id=#{id}
            """)
    int updatePhoneNumberById(@Param("phoneNumber") String phoneNumber, @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET start_year=#{startYear}
            WHERE id=#{id}
            """)
    int updateStartYearById(@Param("startYear") Integer startYear, @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET state=#{state}
            WHERE id=#{id}
            """)
    int updateStateById(@Param("state") Integer state, @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET username=#{username},
                password=#{password},
               `name`=#{name},
                organization_id=#{organizationId},
                phone_number=#{phoneNumber},
                start_year=#{startYear},
                status=#{status}
            WHERE id=#{id}
            """)
    int updateById(UserDo userDo);

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id, create_time, update_time, status
            FROM `user`
            WHERE username=#{username} AND state=#{state}
            """)
    UserDo getByUsernameAndState(@Param("username") String username, @Param("state") Integer state);

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id, create_time, update_time, status
            FROM `user`
            WHERE id=#{id} AND state=#{state}
            """)
    UserDo getByIdAndStatus(@Param("id") Integer id, @Param("state") Integer state);

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id, create_time, update_time, status 
            FROM `user`
            WHERE id=#{id}
            """)
    UserDo getById(@Param("id") Integer id);

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id,organization_name, create_time, update_time, status 
            FROM `user_info` 
            WHERE status=#{status}
            """)
    List<UserVo> listByStatus(@Param("status") Integer status);

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id,organization_name, create_time, update_time, status 
            FROM `user_info` 
            WHERE status=#{status} AND state=#{state}
            """)
    List<UserVo> listByStatusAndState(@Param("status") Integer status,
                                      @Param("state") Integer state);

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id,organization_name, create_time, update_time, status 
            FROM `user_info` 
            WHERE state=#{state}
            """)
    List<UserVo> listByState(@Param("state") Integer state);

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id,organization_name, create_time, update_time, status 
            FROM `user_info` 
            WHERE state=#{state} AND status>=#{status}
            """)
    List<UserVo> listByStateOrdinary(@Param("state") Integer state, @Param("status") Integer status);

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id,organization_name, create_time, update_time, status 
            FROM `user_info` 
            """)
    List<UserVo> list();

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id,organization_name, create_time, update_time, status 
            FROM `user_info` 
            WHERE start_year=#{startYear} AND status<=#{status}
            """)
    List<UserVo> listByStartYear(@Param("startYear") Integer startYear, @Param("status") Integer status);

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id,organization_name, create_time, update_time, status 
            FROM `user_info` 
            WHERE start_year>=#{startYear} AND state=#{state}
            """)
    List<UserVo> listByTime(@Param("startYear") Integer startYear,
                            @Param("state") Integer state);

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id, organization_name, create_time, update_time, status 
            FROM `user_info` 
            WHERE name LIKE CONCAT('%', #{name}, '%') AND state = #{state}
            """)
    List<UserVo> listByName(@Param("name") String name,
                            @Param("state") Integer state);


    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id, create_time, update_time, status 
            FROM `user`
            WHERE username=#{username} AND password=#{password}
            """)
    UserDo login(@Param("username") String username, @Param("password") String password);

    @Select("""
            SELECT DISTINCT start_year 
            FROM `user` 
            WHERE state = #{state}
            AND start_year > 2000
            """
    )
    List<String> listAllStartYears(@Param("state") Integer state);


}
