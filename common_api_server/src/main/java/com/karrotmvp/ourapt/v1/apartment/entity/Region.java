package com.karrotmvp.ourapt.v1.apartment.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Region {
    @Column(name = "region_id")
    private String id;

    @Column(name = "region_name")
    private String name;
}
