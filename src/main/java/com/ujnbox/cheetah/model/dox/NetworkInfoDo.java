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
public class NetworkInfoDo {
    private Integer id;
    private Integer organizationId;
    private String gatewayIp;
    private String subnetMask;
    private String description;
    private String startIp;
    private String endIp;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
