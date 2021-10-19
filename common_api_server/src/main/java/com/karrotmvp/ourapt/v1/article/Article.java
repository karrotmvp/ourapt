package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.User;

import javax.persistence.*;

@MappedSuperclass
public abstract class Article extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "writer_id", referencedColumnName = "karrot_id")
    private User user;
}
