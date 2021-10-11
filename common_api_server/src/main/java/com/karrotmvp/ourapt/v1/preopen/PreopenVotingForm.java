package com.karrotmvp.ourapt.v1.preopen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.User;

import lombok.Getter;
import lombok.Setter;

@Table(name = "preopen_form")
@Entity
@Getter
@Setter
public class PreopenVotingForm extends BaseEntity {

  @Id
  @Column(name = "karrot_id")
  private String karrotId;
  
  @OneToOne(fetch = FetchType.LAZY) // cascade type
  @JoinColumn(name = "karrot_id")
  @MapsId
  private User user;

  @Column(name = "want_supply_checked")
  private Boolean wantSupplyChecked;

  @Column(name = "want_demand_checked")
  private Boolean wantDemandChecked;
  
  @Column(name = "just_fun_checked")
  private Boolean justFunChecked;
  
}
