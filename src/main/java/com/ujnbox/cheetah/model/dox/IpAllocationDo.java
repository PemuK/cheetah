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
public class IpAllocationDo {
    private Integer id;
    private Integer networkInfoId;
    private String ipAddress;
    private String clientName;
    private String phoneNumber;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
