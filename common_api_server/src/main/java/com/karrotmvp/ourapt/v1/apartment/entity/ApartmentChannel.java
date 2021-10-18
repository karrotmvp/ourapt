package com.karrotmvp.ourapt.v1.apartment.entity;

import com.karrotmvp.ourapt.v1.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;

public class ApartmentChannel extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "depth_level")
    private String depthLevel;
}
