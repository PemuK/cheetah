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
    private String phoneNumber;
    private String unit;
    private String room;
    private String buildingName;
    private String adderName;
    private String reportName;
    private Integer maintType;
    private String maintDescription;
    private String locationDescription;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
