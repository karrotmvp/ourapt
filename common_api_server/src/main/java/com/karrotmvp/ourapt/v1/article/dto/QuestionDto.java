package com.karrotmvp.ourapt.v1.article.dto;

import com.karrotmvp.ourapt.v1.user.dto.KarrotOApiUserDto;
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
    private KarrotOApiUserDto writer;
    private Date createdAt;
    private Date updatedAt;
}
