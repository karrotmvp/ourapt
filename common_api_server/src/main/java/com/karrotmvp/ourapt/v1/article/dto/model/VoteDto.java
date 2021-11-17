package com.karrotmvp.ourapt.v1.article.dto.model;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;

import javax.validation.constraints.NotNull;
import java.util.Date;

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
  private Date createdAt;
  @NotNull
  private Date updatedAt;
  @NotNull
  private int countOfComments;
}
