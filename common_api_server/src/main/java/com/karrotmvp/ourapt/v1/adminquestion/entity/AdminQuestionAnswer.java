package com.karrotmvp.ourapt.v1.adminquestion.entity;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "admin_question_answer")
@Entity
public class AdminQuestionAnswer extends BaseEntity {

  @Id
  @Getter
  private String id;

  @Column(name = "main_text")
  @Getter
  @Setter
  private String mainText;

  @Column(name = "region_id")
  @Setter
  private String createdOn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id", referencedColumnName = "id")
  @Setter
  private AdminQuestion about;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "answerer_id", referencedColumnName = "karrot_id")
  @Setter
  private User answerer;

  public AdminQuestionAnswer() {
    this.id = UUID.randomUUID().toString();
  }

  public KarrotProfile getAnswerer() {
    return this.answerer.getProfile();
  }
}
