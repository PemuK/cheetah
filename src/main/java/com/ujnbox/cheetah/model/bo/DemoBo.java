package com.ujnbox.cheetah.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoBo {
    private Integer id;
    private String name;
    private String address;
    private Integer age;
    private String description;
}
