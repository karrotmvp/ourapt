package com.karrotmvp.ourapt.v1.preopen.dto;

import com.karrotmvp.ourapt.v1.preopen.PreopenVotingForm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreopenVotingFormDto {
  private Boolean wantSupplyChecked;

  private Boolean wantDemandChecked;
  
  private Boolean justFunChecked;

  public PreopenVotingForm toEntity() {
    PreopenVotingForm entity = new PreopenVotingForm();
    entity.setWantSupplyChecked(wantSupplyChecked);
    entity.setWantDemandChecked(wantDemandChecked);
    entity.setJustFunChecked(justFunChecked);
    return entity;
  }

  public static PreopenVotingFormDto fromEntity(PreopenVotingForm entity) {
    PreopenVotingFormDto dto = new PreopenVotingFormDto();
    dto.setWantSupplyChecked(entity.getWantSupplyChecked());
    dto.setWantDemandChecked(entity.getWantDemandChecked());
    dto.setJustFunChecked(entity.getJustFunChecked());
    return dto;
  }
}
