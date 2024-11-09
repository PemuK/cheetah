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
    private Integer reporterId;
    private Integer maintType;
    private String maintDescription;
    private String locationDescription;
    private String note;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer state;
    private LocalDateTime finishTime;
    private String completer;
}
