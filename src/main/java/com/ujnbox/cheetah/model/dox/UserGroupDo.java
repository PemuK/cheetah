package com.ujnbox.cheetah.model.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGroupDo {
    private Integer id;
    private Integer userId;
    private Integer groupName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
