package com.karrotmvp.ourapt.v1.adminquestion.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class AnswerAdminQuestionDto {
  @NotNull
  @NotEmpty
  private String mainText;

  @NotNull
  @NotEmpty
  private String regionId;

  @NotNull
  @NotEmpty
  private String apartmentId;
}
