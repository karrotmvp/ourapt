package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.dto.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.ApartmentsInRegionDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.apartment.entity.Region;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
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
    @ApiOperation(value = "depth3 또는 depth4 region_id로 아파트 정보 가져오기 (사전 오픈 용)")
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
            @RequestBody ApartmentDto apt
    ) {
        Apartment newApartment = new Apartment();
        newApartment.setName(apt.getKeyName());
        newApartment.setRegionDepth1(new Region(apt.getRegionHashDepth1(), apt.getNameDepth1()));
        newApartment.setRegionDepth2(new Region(apt.getRegionHashDepth2(), apt.getNameDepth2()));
        newApartment.setRegionDepth3(new Region(apt.getRegionHashDepth3(), apt.getNameDepth3()));
        newApartment.setRegionDepth4(new Region(apt.getRegionHashDepth4(), apt.getNameDepth4()));
        newApartment.setInactiveAt(new Date());
        apartmentRepository.save(newApartment);

        return CommonResponseBody.<Void>builder()
                .success()
                .build();
    }
}
