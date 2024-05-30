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
public class MaintTypeDo {
    private Integer id;
    private String typeName;
    private Integer workAmount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
