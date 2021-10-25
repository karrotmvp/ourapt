package com.karrotmvp.ourapt.v1.comment;


import com.karrotmvp.ourapt.v1.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "comment")
@Entity
public class Comment extends BaseEntity {

    @Id
    private String id;

    @Column(name = "main_text")
    private String mainText;

    public Comment() {
        this.id = UUID.randomUUID().toString();
    }
}
