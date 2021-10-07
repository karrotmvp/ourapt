package com.karrotmvp.ourapt.v1.preopen.dto;

import com.karrotmvp.ourapt.v1.preopen.PreopenForm;

import lombok.Getter;

@Getter
public class PreopenFormDto {

  private String karrotId;

  private Boolean wantSupplyChecked;

  private Boolean wantDemandChecked;
  
  private Boolean justFunChecked;

  public PreopenForm toEntity() {
    PreopenForm entity = new PreopenForm();
    entity.setKarrotId(karrotId);
    entity.setWantSupplyChecked(wantSupplyChecked);
    entity.setWantDemandChecked(wantDemandChecked);
    entity.setJustFunChecked(justFunChecked);
    return entity;
  }
}
