package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    private final ModelMapper modelMapper;

    public ApartmentService(ApartmentRepository apartmentRepository, ModelMapper modelMapper) {
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
    }

    public List<ApartmentDto> getApartmentsInRegionId(String regionId) {
        List<Apartment> apartmentsMatchedByDepth4 = this.apartmentRepository.findByRegionDepth4Id(regionId);
        if (apartmentsMatchedByDepth4.size() > 0) {
            return apartmentsMatchedByDepth4.stream()
                    .filter(Apartment::isActive)
                    .map((apt) -> modelMapper.map(apt, ApartmentDto.class))
                    .collect(Collectors.toList());
        }

        List<Apartment> apartmentsMatchedByDepth3 = this.apartmentRepository.findByRegionDepth4Id(regionId);
        return apartmentsMatchedByDepth3.stream()
                .filter(Apartment::isActive)
                .map((apt) -> modelMapper.map(apt, ApartmentDto.class))
                .collect(Collectors.toList());
    }
}
