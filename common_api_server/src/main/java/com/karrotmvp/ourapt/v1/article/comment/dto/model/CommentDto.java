package com.karrotmvp.ourapt.v1.article.comment.dto.model;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  @NotNull
  private List<CommentDto> subComments = new ArrayList<>();
}
