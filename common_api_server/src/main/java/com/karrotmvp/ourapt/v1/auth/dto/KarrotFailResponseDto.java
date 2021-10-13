package com.karrotmvp.ourapt.v1.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KarrotFailResponseDto {

  @JsonProperty("error")
  private String error;

  @JsonProperty("error_description")
  private String errorDescription; 
  
}
