package com.karrotmvp.ourapt.v1.preopen.dto;

import com.karrotmvp.ourapt.v1.preopen.entity.PreopenVotingForm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PreopenVotingFormDto {

  @NotNull
  private String regionId;

  @NotNull
  private Boolean wantSupplyChecked;

  @NotNull
  private Boolean wantDemandChecked;

  @NotNull
  private Boolean justFunChecked;

  public PreopenVotingForm toEntity() {
    PreopenVotingForm entity = new PreopenVotingForm();
    entity.setRegionId(regionId);
    entity.setWantSupplyChecked(wantSupplyChecked);
    entity.setWantDemandChecked(wantDemandChecked);
    entity.setJustFunChecked(justFunChecked);
    return entity;
  }

  public static PreopenVotingFormDto fromEntity(PreopenVotingForm entity) {
    PreopenVotingFormDto dto = new PreopenVotingFormDto();
    dto.setRegionId(entity.getRegionId());
    dto.setWantSupplyChecked(entity.getWantSupplyChecked());
    dto.setWantDemandChecked(entity.getWantDemandChecked());
    dto.setJustFunChecked(entity.getJustFunChecked());
    return dto;
  }
}
