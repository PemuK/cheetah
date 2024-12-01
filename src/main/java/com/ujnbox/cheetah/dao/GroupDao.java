package com.ujnbox.cheetah.dao;

import com.ujnbox.cheetah.model.dox.GroupDo;
import com.ujnbox.cheetah.model.dox.PermissionDo;
import com.ujnbox.cheetah.model.dox.UserDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupDao {

    @Select("""
            SELECT id, username, password, name, phone_number, start_year, organization_id, create_time, update_time, status,group_id
            FROM `user`
            WHERE id=#{id} AND state=#{state}
            """)
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "startYear", column = "start_year"),
            @Result(property = "organizationId", column = "organization_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "group", column = "group_id", many = @Many(select = "selectGroupById"))
    })
    UserDo getByIdAndStatus(@Param("id") Integer id, @Param("state") Integer state);

    @Select("SELECT * FROM `group` WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "groupName", column = "group_name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "permissions", column = "id",
                    many = @Many(select = "selectPermissionsByGroupId"))
    })
    GroupDo selectGroupById(@Param("id") Integer id);

    @Select("""
                SELECT * FROM permissions 
             WHERE id IN (
                SELECT permission_id FROM group_permission 
                            WHERE group_id = #{groupId}
            )
            """)
    List<PermissionDo> selectPermissionsByGroupId(@Param("groupId") Integer groupId);
}
