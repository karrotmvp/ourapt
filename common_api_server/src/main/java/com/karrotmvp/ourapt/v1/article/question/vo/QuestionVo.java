package com.karrotmvp.ourapt.v1.article.question.vo;

import com.karrotmvp.ourapt.v1.user.dto.KarrotOApiUserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class QuestionVo {
    private String id;
    private String mainText;
    private String regionId;
    private String regionName;
    private KarrotOApiUserDto writer;
    private Date createdAt;
    private Date updatedAt;
}
