package com.karrotmvp.ourapt.v1.apartment.dto;

import com.karrotmvp.ourapt.v1.apartment.vo.ApartmentVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllApartmentsDto {
    private List<ApartmentVo> apartments;
}
