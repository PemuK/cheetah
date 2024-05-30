package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.UserDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Insert("""
            INSERT INTO `user`(username,password,organization_id)
            VALUES (#{username},#{password},#{organizationId})
            """)
    int insert(@Param("username")String username,
               @Param("password")String password,
               @Param("organizationId")String organizationId);

    @Update("""
            UPDATE `user`
            SET username=#{username}
            WHERE id=#{id}
            """)
    int updateUserNameById(@Param("username")String username,
                           @Param("id")String id);

    @Update("""
            UPDATE `user`
            SET password=#{password}
            WHERE id=#{id}
            """)
    int updatePasswordById(@Param("password")String password,
                           @Param("id")String id);

    @Update("""
            UPDATE `user`
            SET name=#{name}
            WHERE id=#{id}
            """)
    int updateNameById(@Param("name")String name,
                       @Param("id")String id);

    @Update("""
            UPDATE `user`
            SET age=#{age}
            WHERE id=#{id}
            """)
    int updateAgeById(@Param("age")String age,
                      @Param("id")String id);

    @Update("""
            UPDATE `user`
            SET gender=#{gender}
            WHERE id=#{id}
            """)
    int updateGenderById(@Param("gender")String gender,
                         @Param("id")String id);

    @Update("""
            UPDATE `user`
            SET phone_number=#{phoneNumber}
            WHERE id=#{id}
            """)
    int updatePhoneNumberById(@Param("phoneNumber")String phoneNumber,
                              @Param("id")String id);

    @Update("""
            UPDATE `user`
            SET start_year=#{start_year}
            WHERE id=#{id}
            """)
    int updateStartYearById(@Param("startYear")String startYear,
                            @Param("id")String id);

    @Update("""
            UPDATE `user`
            SET status=0
            WHERE id=#{id}
            """)
    int lockUserById(@Param("id")Integer id);

    @Update("""
            UPDATE `user`
            SET status=-1
            WHERE id=#{id}
            """)
    int deleteUserById(@Param("id")Integer id);

    @Update("""
            UPDATE `user`
            SET status=1
            WHERE id=#{id}
            """)
    int activateUserById(@Param("id")Integer id);

    @Select("""
            SELECT id,username,password,name,age,gender,phone_number,start_year,organization_id,create_time,update_time,status 
            FROM `user`
            WHERE id=#{id}
            """)
    UserDo getById(@Param("id")String id);

    @Select("""
            SELECT id,username,password,name,age,gender,phone_number,start_year,organization_id,create_time,update_time,status 
            FROM user
            """)
    List<UserDo>getAllUserInfo();
}
