package com.karrotmvp.ourapt.v1.adminquestion.dto.model;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class AdminQuestionAnswerDto {
    @NotNull
    private String id;
    @NotNull
    private KarrotProfile answerer;
    @NotNull
    private String mainText;
    @NotNull
    private Date createdAt;
    @NotNull
    private Date updatedAt;
}
