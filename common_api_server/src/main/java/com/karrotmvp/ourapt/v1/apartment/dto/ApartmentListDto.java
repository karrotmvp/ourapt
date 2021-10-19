package com.karrotmvp.ourapt.v1.apartment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentListDto {
   @ApiModelProperty(value = "받은 region_id의 depth", notes = "depth3일 경우 apartments의 길이가 1이상, depth4일 경우 1")
   private int regionDepth;

   @ApiModelProperty(value = "받은 region_id에 속하는 아파트", notes = "regionDepth가 3 일 경우 한개이상, 4일 경우 한개")
   private List<ApartmentDto> apartments;
}
