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
public class UserProfileDo {
    private Integer id;
    private Integer userId;
    private String name;
    private Integer age;
    private Integer gender;
    private String phoneNumber;
    private Integer startYear;
    private Integer organizationId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
