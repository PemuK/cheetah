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
public class WorkAmountVo {
    private Integer userId;
    private String name;
    private Integer startYear;
    private Integer workAmount;
    private Integer recordAmount;
}
