package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.UserDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Insert("""
            INSERT INTO `user`(username, password, organization_id)
            VALUES (#{username}, #{password}, #{organizationId})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserDo userDo);

    @Update("""
            UPDATE `user`
            SET username=#{username}
            WHERE id=#{id}
            """)
    int updateUsernameById(@Param("username") String username,
                           @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET password=#{password}
            WHERE id=#{id}
            """)
    int updatePasswordById(@Param("password") String password,
                           @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET name=#{name}
            WHERE id=#{id}
            """)
    int updateNameById(@Param("name") String name,
                       @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET age=#{age}
            WHERE id=#{id}
            """)
    int updateAgeById(@Param("age") Integer age,
                      @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET gender=#{gender}
            WHERE id=#{id}
            """)
    int updateGenderById(@Param("gender") String gender,
                         @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET phone_number=#{phoneNumber}
            WHERE id=#{id}
            """)
    int updatePhoneNumberById(@Param("phoneNumber") String phoneNumber,
                              @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET start_year=#{startYear}
            WHERE id=#{id}
            """)
    int updateStartYearById(@Param("startYear") Integer startYear,
                            @Param("id") Integer id);

    @Update("""
            UPDATE `user`
            SET status=#{status}
            WHERE id=#{id}
            """)
    int updateUserStatusById(@Param("status") Integer status,
                             @Param("id") Integer id);

    @Select("""
            SELECT id, username, password, name, age, gender, phone_number, start_year, organization_id, create_time, update_time, status 
            FROM `user`
            WHERE id=#{id} AND status=#{status}
            """)
    UserDo getByIdAndStatus(@Param("id") Integer id,
                            @Param("status") Integer status);

    @Select("""
            SELECT id, username, password, name, age, gender, phone_number, start_year, organization_id, create_time, update_time, status 
            FROM `user` 
            WHERE status=#{status}
            """)
    List<UserDo> listByStatus(@Param("status") Integer status);

    @Select("""
            SELECT id, username, password, name, age, gender, phone_number, start_year, organization_id, create_time, update_time, status 
            FROM `user`
            WHERE username=#{username} AND password=#{password}
            """)
    UserDo login(@Param("username") String username,
                 @Param("password") String password);

}
