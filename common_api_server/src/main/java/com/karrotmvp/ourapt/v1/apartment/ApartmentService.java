package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.common.exception.application.NotServicedRegionException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentService {

  private final ApartmentRepository apartmentRepository;

  private final ModelMapper mapper;

  public ApartmentService(ApartmentRepository apartmentRepository, ModelMapper modelMapper) {
    this.apartmentRepository = apartmentRepository;
    this.mapper = modelMapper;
  }

  public List<ApartmentDto> getApartmentsInRegionId(String regionId) {
    if (!Static.regionDict.containsKey(regionId)) {
      throw new NotServicedRegionException();
    }

    List<Apartment> apartmentsMatchedByDepth4 = this.apartmentRepository.findByRegionDepth4Id(regionId);
    if (apartmentsMatchedByDepth4.size() > 0) {
      return apartmentsMatchedByDepth4.stream()
        .filter(Apartment::isActive)
        .map((apt) -> mapper.map(apt, ApartmentDto.class))
        .collect(Collectors.toList());
    }

    List<Apartment> apartmentsMatchedByDepth3 = this.apartmentRepository.findByRegionDepth3Id(regionId);
    return apartmentsMatchedByDepth3.stream()
      .filter(Apartment::isActive)
      .map((apt) -> mapper.map(apt, ApartmentDto.class))
      .collect(Collectors.toList());
  }

  public List<ApartmentDto> getAvailableApartments() {
    return this.apartmentRepository.findByInactiveAtIsNull().stream()
      .map(apt -> mapper.map(apt, ApartmentDto.class))
      .collect(Collectors.toList());
  }

  public ApartmentDto getApartmentById(String apartmentId) {
    Apartment found = this.apartmentRepository.findById(apartmentId)
      .orElseThrow(() -> new DataNotFoundFromDBException("유효하지 않은 아파트ID 입니다."));
    return mapper.map(found, ApartmentDto.class);
  }
}
