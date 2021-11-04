package com.karrotmvp.ourapt.v1.apartment.dto.response;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetAvailableApartmentsDto {
    @NotNull
    private List<ApartmentDto> apartments;
}
