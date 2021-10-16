package com.karrotmvp.ourapt.v1.apartment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApartmentDto {
    private Long id;
    private String regionHash;
    private String name;
    private String friendlyName;
}
