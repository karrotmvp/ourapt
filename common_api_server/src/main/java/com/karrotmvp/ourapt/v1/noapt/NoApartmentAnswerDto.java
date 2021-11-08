package com.karrotmvp.ourapt.v1.noapt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class NoApartmentAnswerDto {

  @NotNull
  private String answer;
}
