package com.karrotmvp.ourapt.v1.preopen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.User;

import lombok.Getter;
import lombok.Setter;

@Table(name = "preopen")
@Entity
@Getter
@Setter
public class Preopen extends BaseEntity {
  
  @Id
  @Column(name = "karrot_id")
  private String karrotId;
  
  @OneToOne(fetch = FetchType.LAZY) // cascade type
  @PrimaryKeyJoinColumn(name = "karrot_id")
  private User user;

  @Column(name = "want_supply_checked")
  private Boolean wantSupplyChecked;

  @Column(name = "want_demand_checked")
  private Boolean wantDemandChecked;
  
  @Column(name = "just_fun_checked")
  private Boolean justFunChecked;
  
}
