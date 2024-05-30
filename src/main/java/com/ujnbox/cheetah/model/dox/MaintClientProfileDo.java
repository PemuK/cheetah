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
public class MaintClientProfileDo {
    private Integer id;
    private String clientName;
    private String phoneNumber;
    private String unit;
    private String room;
    private Integer buildingId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
