package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.UserDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Insert("""
            INSERT INTO `user`
            VALUES (#{username},#{password})
            """)
    int insert(@Param("username")String username,
               @Param("password")String password);

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

    @Select("""
            SELECT * FROM `user`
            WHERE id=#{id}
            """)
    UserDo getById(@Param("id")String id);

    @Select("""
            SELECT * FROM user
            """)
    List<UserDo>getAllUserInfo();
}
