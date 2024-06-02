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
    private Integer amount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;
}
