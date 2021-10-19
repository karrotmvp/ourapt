package com.karrotmvp.ourapt.v1.apartment.dto;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class ApartmentChannelDto extends BaseEntity {

    private String name;

    private int depthLevel;
}
