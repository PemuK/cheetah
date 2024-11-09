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
public class UserVo {
    private Integer id;
    private String username;
    private String name;
    private String phoneNumber;
    private Integer startYear;
    private Integer organizationId;
    private String organizationName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String token;
    private Integer status;
    private String permission;
}
