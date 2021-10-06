package com.karrotmvp.umeapartment.v1.preopen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.karrotmvp.umeapartment.v1.common.BaseEntity;
import com.karrotmvp.umeapartment.v1.user.User;

import lombok.Getter;
import lombok.Setter;

@Table(name = "preopen")
@Entity
@Getter
@Setter
public class Preopen extends BaseEntity {
  
  @Id
  @Column(name = "daangn_id")
  private String daangnId;
  
  @OneToOne(fetch = FetchType.LAZY) // cascade type
  @JoinColumn(name = "daangn_id")
  private User user;

  @Column(name = "want_supply_checked")
  private Boolean wantSupplyChecked;

  @Column(name = "want_demand_checked")
  private Boolean wantDemandChecked;
  
  @Column(name = "just_fun_checked")
  private Boolean justFunChecked;

  @Column(name = "test")
  private String test;
  
}
