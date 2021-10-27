package com.karrotmvp.ourapt.v1.preopen.dto;

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
}
