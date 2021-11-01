package com.karrotmvp.ourapt.v1.article.question.dto.model;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class QuestionDto {
    @NotNull
    private String id;
    @NotNull
    private KarrotProfile writer;
    @NotNull
    private String mainText;
    @NotNull
    private Boolean isPinned;
    @NotNull
    private Boolean byAdmin;
    @NotNull
    private Date createdAt;
    @NotNull
    private Date updatedAt;
}
