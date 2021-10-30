package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.response.GetApartmentInRegionDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = "아파트")
public class ApartmentController {

  private final ApartmentService apartmentService;

  public ApartmentController(ApartmentService apartmentService) {
    this.apartmentService = apartmentService;
  }

  @GetMapping(value = "/apartments")
  @ApiOperation(value = "해당 리전에 포함된 아파트들 가져오기")
  public CommonResponseBody<GetApartmentInRegionDto> getApartmentInRegion(
    @RequestParam String regionId
  ) {
    List<ApartmentDto> apartmentsInRegion = this.apartmentService.getApartmentsInRegionId(regionId);

    return CommonResponseBody.<GetApartmentInRegionDto>builder()
      .success()
      .data(new GetApartmentInRegionDto(apartmentsInRegion))
      .build();
  }
}
