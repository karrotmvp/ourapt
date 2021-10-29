package com.karrotmvp.ourapt.v1.adminquestion.dto;

import com.karrotmvp.ourapt.v1.apartment.dto.RegionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class AdminQuestionDto {
    private String id;
    private String mainText;
    private Date expiredAt;
    private Boolean isActive;
    private RegionDto displayOn;
    private Date createdAt;
    private Date updatedAt;
}
