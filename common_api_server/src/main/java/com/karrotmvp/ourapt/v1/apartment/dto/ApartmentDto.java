package com.karrotmvp.ourapt.v1.apartment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApartmentDto {
    private Long id;
    private String name;
    private String friendlyName;
    private String regionHashDepth1;
    private String regionHashDepth2;
    private String regionHashDepth3;
    private String regionHashDepth4;
}
