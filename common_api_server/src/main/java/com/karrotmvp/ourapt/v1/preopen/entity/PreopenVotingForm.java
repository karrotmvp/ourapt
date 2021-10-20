package com.karrotmvp.ourapt.v1.preopen.entity;

import javax.persistence.*;

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

  @Column(name = "region_id")
  private String regionId;
  
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "karrot_id", referencedColumnName = "karrot_id")
  @MapsId
  private User user;

  @Column(name = "want_supply_checked")
  private Boolean wantSupplyChecked;

  @Column(name = "want_demand_checked")
  private Boolean wantDemandChecked;
  
  @Column(name = "just_fun_checked")
  private Boolean justFunChecked;

  public void setUser(User user) {
    this.karrotId = user.getKarrotId();
    this.user = user;
  }

  public void setResult(boolean supply, boolean demand, boolean fun) {
    this.setWantSupplyChecked(supply);
    this.setWantDemandChecked(demand);
    this.setJustFunChecked(fun);
  }
  
}
