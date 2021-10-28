package com.karrotmvp.ourapt.v1.preopen.entity;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "preopen_form")
@Entity
@Getter
@Setter
public class PreopenForm extends BaseEntity {

  @Id
  @Column(name = "karrot_id")
  private String userId;

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
    this.userId = user.getId();
    this.user = user;
  }

  public void setResult(boolean supply, boolean demand, boolean fun) {
    this.setWantSupplyChecked(supply);
    this.setWantDemandChecked(demand);
    this.setJustFunChecked(fun);
  }
  
}
