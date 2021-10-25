package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.dto.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.ApartmentsInRegionDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.common.dto.CommonResponseBody;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1/apartment")
public class ApartmentController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ApartmentFindService apartmentFindService;

    @Autowired
    ApartmentRepository apartmentRepository;


    @GetMapping(value = "")
    @ApiOperation(value = "depth3 또는 depth4 region_id로 아파트 정보 가져오기")
    public CommonResponseBody<ApartmentsInRegionDto> getApartmentByRegionId(
            @RequestParam(name = "regionId") String regionId
    ) {
        return CommonResponseBody.<ApartmentsInRegionDto>builder()
                .success()
                .data(apartmentFindService
                        .findApartmentsInRegionId(regionId))
                .build();
    }

    @PostMapping(value = "")
    @ApiOperation(value = "서비스 하는 아파트 추가 [관리자용]")
    @Transactional
    public CommonResponseBody<Void> addApartment(
            @RequestBody ApartmentDto apartment
    ) {
        Apartment newApartment = new Apartment();
        newApartment.setKeyName(apartment.getKeyName());
        newApartment.setNameDepth1(apartment.getNameDepth1());
        newApartment.setRegionHashDepth1(apartment.getRegionHashDepth1());
        newApartment.setNameDepth2(apartment.getNameDepth2());
        newApartment.setRegionHashDepth2(apartment.getRegionHashDepth2());
        newApartment.setNameDepth3(apartment.getNameDepth3());
        newApartment.setRegionHashDepth3(apartment.getRegionHashDepth3());
        newApartment.setNameDepth4(apartment.getNameDepth4());
        newApartment.setRegionHashDepth4(apartment.getRegionHashDepth4());
        newApartment.setInactiveAt(new Date());
        apartmentRepository.save(newApartment);

        return CommonResponseBody.<Void>builder()
                .success()
                .build();
    }
}
