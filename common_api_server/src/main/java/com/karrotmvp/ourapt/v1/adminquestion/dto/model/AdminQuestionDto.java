package com.karrotmvp.ourapt.v1.adminquestion.dto.model;

import com.karrotmvp.ourapt.v1.apartment.dto.model.RegionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class AdminQuestionDto {
    @NotNull
    private String id;
    @NotNull
    private String mainText;
    @NotNull
    private Boolean isActive;
    @NotNull
    private RegionDto displayOn;
    @NotNull
    private Date expiredAt;
    @NotNull
    private Date createdAt;
    @NotNull
    private Date updatedAt;
}
