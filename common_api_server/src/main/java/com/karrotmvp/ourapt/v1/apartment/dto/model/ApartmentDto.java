package com.karrotmvp.ourapt.v1.apartment.dto.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class ApartmentDto {
    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private RegionDto regionDepth1;

    @NotNull
    private RegionDto regionDepth2;

    @NotNull
    private RegionDto regionDepth3;

    private RegionDto regionDepth4;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Date createdAt;

    @NotNull
    private Date updatedAt;

    public void setRegions(List<RegionDto> regions) {
        if (regions.size() != 3 && regions.size() != 4) {
            throw new RuntimeException("Num of regions for setter must be 3 or 4");
        }
        this.regionDepth1 = regions.get(0);
        this.regionDepth2 = regions.get(1);
        this.regionDepth3 = regions.get(2);
        this.regionDepth4 = regions.get(3);
    }
}
