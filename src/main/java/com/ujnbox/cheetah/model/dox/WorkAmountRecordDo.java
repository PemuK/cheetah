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
public class WorkAmountRecordDo {
    private Integer id;
    private Integer userId;
    private Integer weekAmount;
    private Integer mountAmount;
    private Integer quarterAmount;
    private Integer totalAmount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
