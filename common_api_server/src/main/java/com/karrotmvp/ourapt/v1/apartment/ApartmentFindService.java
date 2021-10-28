package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.dto.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.ApartmentsInRegionDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentFindService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ApartmentsInRegionDto findApartmentsInRegionId(String regionId) {
        List<Apartment> apartmentsMatchedByDepth4 = this.apartmentRepository.findByRegionHashDepth4(regionId);
        if (apartmentsMatchedByDepth4.size() > 0) {
            return new ApartmentsInRegionDto(4, apartmentsMatchedByDepth4
                    .stream()
                    .filter(Apartment::isActive)
                    .map((apt) -> modelMapper.map(apt, ApartmentDto.class))
                    .collect(Collectors.toList())
            );
        }

        List<Apartment> apartmentsMatchedByDepth3 = this.apartmentRepository.findByRegionHashDepth3(regionId);
        return new ApartmentsInRegionDto(3, apartmentsMatchedByDepth3
                .stream()
                .filter(Apartment::isActive)
                .map((apt) -> modelMapper.map(apt, ApartmentDto.class))
                .collect(Collectors.toList())
        );
    }

}
