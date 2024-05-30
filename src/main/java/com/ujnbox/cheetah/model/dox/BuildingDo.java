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
public class BuildingDo {
    private Integer id;
    private String buildingName;
    private Integer type;
    private Integer campus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;
}
