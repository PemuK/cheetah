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
    int insert(@Param("username") String username,
               @Param("password") String password,
               @Param("organizationId") String organizationId);

    @Update("""
            UPDATE `user`
            SET username=#{username}
            WHERE id=#{id}
            """)
    int updateUsernameById(@Param("username") String username,
                           @Param("id") String id);

    @Update("""
            UPDATE `user`
            SET password=#{password}
            WHERE id=#{id}
            """)
    int updatePasswordById(@Param("password") String password,
                           @Param("id") String id);

    @Update("""
            UPDATE `user`
            SET name=#{name}
            WHERE id=#{id}
            """)
    int updateNameById(@Param("name") String name,
                       @Param("id") String id);

    @Update("""
            UPDATE `user`
            SET age=#{age}
            WHERE id=#{id}
            """)
    int updateAgeById(@Param("age") int age,
                      @Param("id") String id);

    @Update("""
            UPDATE `user`
            SET gender=#{gender}
            WHERE id=#{id}
            """)
    int updateGenderById(@Param("gender") String gender,
                         @Param("id") String id);

    @Update("""
            UPDATE `user`
            SET phone_number=#{phoneNumber}
            WHERE id=#{id}
            """)
    int updatePhoneNumberById(@Param("phoneNumber") String phoneNumber,
                              @Param("id") String id);

    @Update("""
            UPDATE `user`
            SET start_year=#{startYear}
            WHERE id=#{id}
            """)
    int updateStartYearById(@Param("startYear") int startYear,
                            @Param("id") String id);

    @Update("""
            UPDATE `user`
            SET status=#{status}
            WHERE id=#{id}
            """)
    int updateUserStatusById(@Param("status") int status,
                             @Param("id") String id);

    @Select("""
            SELECT id, username, password, name, age, gender, phone_number, start_year, organization_id, create_time, update_time, status 
            FROM `user`
            WHERE id=#{id}
            """)
    UserDo getById(@Param("id") String id);

    @Select("""
            SELECT id, username, password, name, age, gender, phone_number, start_year, organization_id, create_time, update_time, status 
            FROM `user`
            """)
    List<UserDo> getAllUserInfo();
}
