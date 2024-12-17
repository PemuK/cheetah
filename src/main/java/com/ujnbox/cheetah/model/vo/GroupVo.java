package com.ujnbox.cheetah.model.vo;

import com.ujnbox.cheetah.model.dox.PermissionDo;
import com.ujnbox.cheetah.model.dox.UserDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupVo {
    private Integer id;
    private String groupName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;
    private List<PermissionDo> permissions;
    private List<UserVo> users;
}
