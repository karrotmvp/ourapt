package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public abstract class ArticleDto {

  @NotNull
  private String id;

  @NotNull
  private KarrotProfile writer;

  @NotNull
  private String mainText;

  @NotNull
  private Boolean byAdmin;

  @NotNull
  private Date createdAt;

  @NotNull
  private Date updatedAt;

}