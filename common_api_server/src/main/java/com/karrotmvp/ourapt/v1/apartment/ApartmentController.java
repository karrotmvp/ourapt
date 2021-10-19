package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.dto.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.ApartmentListDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/apartment")
public class ApartmentController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ApartmentFindService apartmentFindService;

    @GetMapping(value = "/")
    @ApiOperation(value = "region_id로 아파트 정보 가져오기")
    public CommonResponseBody<ApartmentListDto> getApartmentByRegionId(
            @RequestParam(name = "regionId") String regionId
    ) {
        List<Apartment> apartments = apartmentFindService.getApartmentsInRegionId(regionId);

        return CommonResponseBody.<ApartmentListDto>builder()
                .success()
                .data(new ApartmentListDto(
                        apartments.stream()
                                .map((apartment) -> modelMapper.map(apartment, ApartmentDto.class))
                                .collect(Collectors.toList())))
                .build();
    }

    @PostMapping
    @ApiOperation(value = "아파트 추가")
    public CommonResponseBody<Void> addApartment(
            @RequestBody String name,
            @RequestBody String regionHashDepth1,
            @RequestBody String regionHashDepth2,
            @RequestBody String regionHashDepth3,
            @RequestBody String regionHashDepth4,
            @RequestBody String channelName,
            @RequestBody String channelDepthLevel
    ) {
        System.out.println(name + regionHashDepth1 + channelName);
        return CommonResponseBody.<Void>builder()
                    .success()
                    .build();
    }
}
