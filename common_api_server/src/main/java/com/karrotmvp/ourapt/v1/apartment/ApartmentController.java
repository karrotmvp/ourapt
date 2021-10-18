package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.dto.ApartmentListDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/apartment")
public class ApartmentController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ApartmentFindService apartmentFindService;

    @GetMapping(value = "/")
    @ApiOperation(value = "region_id(리전 해시 값)들로 아파트 정보 가져오기")
    public CommonResponseBody<ApartmentListDto> getApartmentByRegionId(
            @RequestParam(name = "regionIds") List<String> regionIds
    ) {
        return CommonResponseBody.<ApartmentListDto>builder()
                .success()
                .data(new ApartmentListDto())
                .build();
    }
}
