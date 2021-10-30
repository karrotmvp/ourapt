package com.karrotmvp.ourapt.v1.adminquestion.dto.response;

import com.karrotmvp.ourapt.v1.adminquestion.dto.model.AdminQuestionDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetAvailableAdminQuestionDto {
  private AdminQuestionDto adminQuestion;
}
