package com.karrotmvp.ourapt.v1.article.question.dto;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class QuestionDto {
    private String id;
    private String mainText;
    private String regionId;
    private String regionName;
    private KarrotProfile writer;
    private Date createdAt;
    private Date updatedAt;
}
