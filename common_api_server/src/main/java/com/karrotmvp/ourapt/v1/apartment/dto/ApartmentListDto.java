package com.karrotmvp.ourapt.v1.apartment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentListDto {
   private List<ApartmentDto> apartments;
}
