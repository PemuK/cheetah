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
public class ParticipateMaintUserDo {
    private Integer id;
    private Integer maintRecordId;
    private Integer userId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
