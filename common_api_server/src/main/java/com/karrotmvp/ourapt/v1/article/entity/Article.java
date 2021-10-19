package com.karrotmvp.ourapt.v1.article.entity;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public abstract class Article extends BaseEntity {

    @Id
    @Getter
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", referencedColumnName = "karrot_id")
    @Getter
    @Setter
    private User user;

    @Column(name = "region_id")
    @Getter
    @Setter
    private String regionId;

    public Article() {
        this.id = UUID.randomUUID().toString();
    }
}
