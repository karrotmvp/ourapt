package com.karrotmvp.umeapartment.v1.preopen;

import lombok.Getter;

@Getter
public class PreopenDto {

  private String daangnId;

  private Boolean wantSupplyChecked;

  private Boolean wantDemandChecked;
  
  private Boolean justFunChecked;
  

  public Preopen toEntity() {
    Preopen entity = new Preopen();
    entity.setDaangnId(daangnId);
    entity.setWantSupplyChecked(wantSupplyChecked);
    entity.setWantDemandChecked(wantDemandChecked);
    entity.setJustFunChecked(justFunChecked);
    return entity;
  }
}