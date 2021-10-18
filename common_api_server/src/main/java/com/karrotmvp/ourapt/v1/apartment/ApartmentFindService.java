package com.karrotmvp.ourapt.v1.apartment;

import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentFindService {

    private ApartmentRepository apartmentRepository;

    public List<Apartment> getApartmentsInRegionId(String regionId) {
        List<Apartment> apartmentsMatchedByDepth4 = this.apartmentRepository.findByRegionHashDepth4(regionId);
        if (apartmentsMatchedByDepth4.size() > 0) {
            return apartmentsMatchedByDepth4;
        }
        return this.apartmentRepository.findByRegionHashDepth3(regionId);
    }

}
