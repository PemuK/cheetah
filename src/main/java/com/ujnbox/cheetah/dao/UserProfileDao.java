package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.UserProfileDo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserProfileDao {
    @Insert("""
            INSERT INTO user_profile(user_id,name,organization_id)
            VALUES (#{userId},#{name},#{organizationId})
            """)
    int insert(@Param("userId")Integer userId,
               @Param("name")String name,
               @Param("organizationId")Integer organizationId);

    @Update("""
            UPDATE user_profile
            SET name=#{name}
            WHERE id=#{id}
            """)
    int updateNameById(@Param("name")String name,
                      @Param("id")Integer id);

    @Update("""
            UPDATE user_profile
            SET age=#{age}
            WHERE id=#{id}
            """)
    int updateAgeById(@Param("age")Integer age,
                      @Param("id")Integer id);

    @Update("""
            UPDATE user_profile
            SET gender=#{gender}
            WHERE id=#{id}
            """)
    int updateGenderById(@Param("gender")Integer gender,
                         @Param("id")Integer id);

    @Update("""
            UPDATE user_profile
            SET phone_number=#{phoneNumber}
            WHERE id=#{id}
            """)
    int updatePhoneNumberById(@Param("phoneNumber")String phoneNumber,
                         @Param("id")Integer id);

    @Update("""
            UDPATE user_profile
            SET start_year=#{startYear}
            WHERE id=#{id};
            """)
    int updateStartYearById(@Param("startYear")Integer startYear,
                            @Param("id")String id);

    @Select("""
            SELECT * FROM user_profile
            WHERE user_id=#{userId}
            """)
    UserProfileDo getById(@Param("userId")Integer userId);


}
