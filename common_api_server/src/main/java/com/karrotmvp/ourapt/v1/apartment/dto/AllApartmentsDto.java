package com.karrotmvp.ourapt.v1.apartment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllApartmentsDto {
    private List<ApartmentDto> apartments;
}
