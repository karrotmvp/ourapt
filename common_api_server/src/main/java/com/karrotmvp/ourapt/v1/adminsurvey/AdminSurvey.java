package com.karrotmvp.ourapt.v1.adminsurvey;


import com.karrotmvp.ourapt.v1.common.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "topfeed")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public class AdminSurvey extends BaseEntity {

    @Id
    private String id;

    @Column(name = "question_text")
    private String question_text;

    @Column(name = "apartment_id")
    private String apartmentId;

    @Column(name = "expired_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredAt;

    @Column(name = "inactive_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inactiveAt;

    public AdminSurvey() {
        this.id = UUID.randomUUID().toString();
        this.inactiveAt = new Date();
    }
}
