package com.karrotmvp.ourapt.v1.article.comment.dto.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class CommentDto {
  @NotNull
  private String id;
  @NotNull
  private KarrotProfile writer;
  @NotNull
  private String mainText;
  @NotNull
  private Date createdAt;
  @NotNull
  private Date updatedAt;
}
