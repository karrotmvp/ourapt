package com.karrotmvp.ourapt.v1.comment;


import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.article.Article;
import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "comment", indexes = {
  @Index(columnList = "parent_id")
})
@Entity
public class Comment extends BaseEntity {

    @Id
    @Getter
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", referencedColumnName = "karrot_id")
    @Getter
    @Setter
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    @Setter
    @Getter
    private Apartment apartmentWhereCreated;

    @Column(name = "region_id")
    @Setter
    private String regionWhereCreated;

    @Transient
    @Getter
    @Setter
    private int countOfComments = 0;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date deletedAt;

    public Comment() {
        this.id = UUID.randomUUID().toString();
    }

    @Column(name = "main_text")
    @Getter
    @Setter
    private String mainText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @Setter
    private Article parent;

    public boolean isDeleted() {
        return this.deletedAt != null && this.deletedAt.before(new Date());
    }
}
