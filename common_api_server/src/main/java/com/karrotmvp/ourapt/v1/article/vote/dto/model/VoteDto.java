package com.karrotmvp.ourapt.v1.article.vote.dto.model;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class VoteDto {
  @NotNull
  private String id;
  @NotNull
  private KarrotProfile writer;
  @NotNull
  private String mainText;
  @NotNull
  private Boolean byAdmin;
  @NotNull
  private Boolean isPinned;
  @NotNull
  private List<VoteItemDto> items;
  @NotNull
  private Date createdAt;
  @NotNull
  private Date updatedAt;
  @NotNull
  private int countOfComments;
}
