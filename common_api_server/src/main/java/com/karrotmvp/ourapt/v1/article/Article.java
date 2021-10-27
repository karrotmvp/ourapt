package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "article")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Article extends BaseEntity {

    @Id
    @Getter
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", referencedColumnName = "karrot_id")
    @Getter
    @Setter
    private User writer;

    @Column(name = "region_id")
    @Getter
    @Setter
    private String regionId;

    public Article() {
        this.id = UUID.randomUUID().toString();
    }
}
