package com.karrotmvp.umeapartment.v1.preopen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.karrotmvp.umeapartment.v1.common.BaseEntity;

@Table(name = "preopen")
@Entity
public class Preopen extends BaseEntity {
  
  @Id
  @Column(name = "daangn_id")
  private String daangnId;

  @Column(name = "want_supply_checked")
  private Boolean wantSupplyChecked;

  @Column(name = "want_demand_checked")
  private Boolean wantDemandChecked;
  
  @Column(name = "just_fun_checked")
  private Boolean justFunChecked;
}
