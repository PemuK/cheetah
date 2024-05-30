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
public class MaintRecordDo {
    private Integer id;
    private Integer clientId;
    private Integer adderId;
    private Integer reportId;
    private Integer maintType;
    private String maintDescription;
    private String locationDescription;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime finishTime;
    private Integer state;
}
