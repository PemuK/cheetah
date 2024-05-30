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
public class OrganizationDo {
    private Integer id;
    private String organizationName;
    private String responsiblePerson;
    private String phoneNumber;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
