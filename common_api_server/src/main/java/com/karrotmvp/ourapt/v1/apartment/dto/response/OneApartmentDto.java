package com.karrotmvp.ourapt.v1.apartment.dto.response;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OneApartmentDto {
  private ApartmentDto apartment;
}
