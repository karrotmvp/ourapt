package com.karrotmvp.ourapt.v1.apartment.dto.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private String brandName;

    @NotNull
    private String bannerImage;

    @NotNull
    private RegionDto regionDepth1;

    @NotNull
    private RegionDto regionDepth2;

    @NotNull
    private RegionDto regionDepth3;

    private RegionDto regionDepth4;

    @NotNull
    private boolean isActive;

    @NotNull
    private Date createdAt;

    @NotNull
   private Date updatedAt;
}
