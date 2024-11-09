package com.ujnbox.cheetah.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintRecordVo {
    private Integer id;
    private String clientName;
    private Integer clientId;
    private String phoneNumber;
    private String unit;
    private String room;
    private String buildingName;
    private Integer buildingId;
    private String adderName;
    private Integer adderId;
    private String reporterName;
    private Integer reporterId;
    private Integer maintType;
    private String maintTypeName;
    private Integer workAmount;
    private String maintDescription;
    private String locationDescription;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime finishTime;
    private String note;
    private String completer;
    private String completerName;
}
