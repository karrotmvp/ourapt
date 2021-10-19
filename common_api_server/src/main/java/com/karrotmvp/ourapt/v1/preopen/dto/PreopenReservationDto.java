package com.karrotmvp.ourapt.v1.preopen.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PreopenReservationDto {

    @NotNull
    private String regionId;
}
