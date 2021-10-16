package com.karrotmvp.ourapt.v1.apartment;

import javax.persistence.*;

@Table(name = "apartment")
@Entity
public class Apartment {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "region_hash")
    private String regionHash;

    @Column(name = "name")
    private String name;

    @Column(name = "friendly_name")
    private String friendlyName;
}
