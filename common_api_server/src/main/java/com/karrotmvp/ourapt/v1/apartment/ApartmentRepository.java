package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, String> {

    List<Apartment> findByRegionDepth3Id(String regionId);
    List<Apartment> findByRegionDepth4Id(String regionId);
    List<Apartment> findByInactiveAtIsNull();
}
