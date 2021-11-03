package com.karrotmvp.ourapt.v1.common;

import lombok.Getter;

import java.util.Comparator;

public class BaseEntityCreatedDateComparator implements Comparator<BaseEntity> {

  @Getter
  public enum Order {
    ASC(1),
    DESC(-1);

    private final int value;

    Order(int value) {
      this.value = value;
    }
  }

  private final Order order;

  public BaseEntityCreatedDateComparator(Order order) {
    this.order = order;
  }


  private int compareAsc(BaseEntity e1, BaseEntity e2) {
    if (e2.getCreatedAt().before(e1.getCreatedAt())) {
      return 1;
    } else if (e2.getCreatedAt().equals(e1.getCreatedAt())) {
      return 0;
    } else {
      return -1;
    }
  }

  @Override
  public int compare(BaseEntity e1, BaseEntity e2) {
    return order.getValue() * compareAsc(e1, e2);
  }
}
