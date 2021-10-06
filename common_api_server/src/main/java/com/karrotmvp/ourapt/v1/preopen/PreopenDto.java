package com.karrotmvp.ourapt.v1.preopen;

import lombok.Getter;

@Getter
public class PreopenDto {

  private String karrotId;

  private Boolean wantSupplyChecked;

  private Boolean wantDemandChecked;
  
  private Boolean justFunChecked;
  

  public Preopen toEntity() {
    Preopen entity = new Preopen();
    entity.setKarrotId(karrotId);
    entity.setWantSupplyChecked(wantSupplyChecked);
    entity.setWantDemandChecked(wantDemandChecked);
    entity.setJustFunChecked(justFunChecked);
    return entity;
  }
}
