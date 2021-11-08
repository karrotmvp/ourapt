package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.response.GetAvailableApartmentsDto;
import com.karrotmvp.ourapt.v1.apartment.dto.response.OneApartmentDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = "2. 아파트")
public class ApartmentController {

  private final ApartmentService apartmentService;

  public ApartmentController(ApartmentService apartmentService) {
    this.apartmentService = apartmentService;
  }

  @GetMapping(value = "/apartments")
  @ApiOperation(value = "해당 리전에 포함된 아파트들 가져오기")
  public CommonResponseBody<GetAvailableApartmentsDto> getAvailableApartments() {
    List<ApartmentDto> apartments = this.apartmentService.getAvailableApartments();
    return CommonResponseBody.<GetAvailableApartmentsDto>builder()
      .success()
      .data(new GetAvailableApartmentsDto(apartments))
      .build();
  }

  @GetMapping(value = "/apartment/{apartmentId}")
  @ApiOperation(value = "아파트 ID로 아파트 정보 가져오기")
  public CommonResponseBody<OneApartmentDto> getApartmentById(
    @PathVariable String apartmentId
  ) {
    ApartmentDto apt = this.apartmentService.getApartmentById(apartmentId);
    return CommonResponseBody.<OneApartmentDto>builder()
      .success()
      .data(new OneApartmentDto(apt))
      .build();
  }
}
