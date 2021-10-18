package com.karrotmvp.ourapt.v1.apartment.entity;

import com.karrotmvp.ourapt.v1.common.BaseEntity;

import javax.persistence.*;

@Table(name = "apartment")
@Entity
public class Apartment extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "friendly_name")
    private String friendlyName;

    @Column(name = "region_hash_depth1")
    private String regionHashDepth1;

    @Column(name = "region_hash_depth2")
    private String regionHashDepth2;

    @Column(name = "region_hash_depth3")
    private String regionHashDepth3;

    @Column(name = "region_hash_depth4")
    private String regionHashDepth4;

    @Column(name = "depth_for_group")
    private int depthForGroup;
}
