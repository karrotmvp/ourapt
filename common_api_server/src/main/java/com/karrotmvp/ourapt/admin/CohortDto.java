package com.karrotmvp.ourapt.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CohortDto {
  private Date[] dates;
  private Long[] initialValue;
  private Double[][] data;
}
