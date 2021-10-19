package com.karrotmvp.ourapt.v1.apartment.entity;

import javax.persistence.*;

import com.karrotmvp.ourapt.v1.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "apartment")
@Entity
@Getter
@Setter
public class Apartment extends BaseEntity {

    @Id
    private String id;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "channel_name")
    private String channelName;

    @Column(name = "name_depth1")
    private String nameDepth1;

    @Column(name = "region_hash_depth1")
    private String regionHashDepth1;

    @Column(name = "name_depth2")
    private String nameDepth2;

    @Column(name = "region_hash_depth2")
    private String regionHashDepth2;

    @Column(name = "name_depth3")
    private String nameDepth3;

    @Column(name = "region_hash_depth3")
    private String regionHashDepth3;

    @Column(name = "name_depth4")
    private String nameDepth4;

    @Column(name = "region_hash_depth4")
    private String regionHashDepth4;

    @Column(name = "channel_depth_level")
    private int channelDepthLevel;

    @Column(name = "inactive_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inactiveAt;

    public Apartment() {
        this.id = UUID.randomUUID().toString();
    }

    public Boolean isActive(){
        return this.inactiveAt == null || this.inactiveAt.before(new Date());
    }
}
