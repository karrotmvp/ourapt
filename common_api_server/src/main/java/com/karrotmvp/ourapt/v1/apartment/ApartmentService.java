package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
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

  public List<ApartmentDto> getAvailableApartments() {
    return this.apartmentRepository.findByInactiveAtIsNull().stream()
      .map(apt -> mapper.map(apt, ApartmentDto.class))
      .collect(Collectors.toList());
  }

  public List<ApartmentDto> getAllApartments() {
    return this.apartmentRepository.findAll().stream()
      .map(apt -> mapper.map(apt, ApartmentDto.class))
      .collect(Collectors.toList());
  }

  public ApartmentDto getApartmentById(String apartmentId) {
    return mapper.map(safelyGetApartmentEntityById(apartmentId), ApartmentDto.class);
  }

  public void inactivateApartmentById(String apartmentId) {
    Apartment targetApt = safelyGetApartmentEntityById(apartmentId);
    targetApt.setInactiveAt(new Date());
    this.apartmentRepository.save(targetApt);
  }

  public void activateApartmentById(String apartmentId) {
    Apartment targetApt = safelyGetApartmentEntityById(apartmentId);
    targetApt.setInactiveAt(null);
    this.apartmentRepository.save(targetApt);
  }


  private Apartment safelyGetApartmentEntityById(String apartmentId) {
    return this.apartmentRepository.findById(apartmentId)
      .orElseThrow(() -> new DataNotFoundFromDBException(apartmentId + "에 해당하는 아파트가 없습니다."));
  }
}
