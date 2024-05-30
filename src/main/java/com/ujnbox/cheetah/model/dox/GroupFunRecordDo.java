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
public class GroupFunRecordDo {
    private Integer id;
    private Integer function;
    private Integer userGroupId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;
}
