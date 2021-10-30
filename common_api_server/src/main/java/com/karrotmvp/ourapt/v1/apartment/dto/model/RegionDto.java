package com.karrotmvp.ourapt.v1.apartment.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegionDto {
    @NotNull
    private String id;
    @NotNull
    private String name;
}
