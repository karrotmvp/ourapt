package com.karrotmvp.ourapt.v1.adminquestion.entity;


import com.karrotmvp.ourapt.v1.apartment.entity.Region;
import com.karrotmvp.ourapt.v1.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "admin_question")
@Entity
public class AdminQuestion extends BaseEntity {

    @Id
    @Getter
    private String id;

    @Column(name = "main_text")
    @Getter
    @Setter
    private String mainText;

    @Column(name = "expired_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date expiredAt;

    @Column(name = "inactive_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private Date inactiveAt;

    @Embedded
    @Getter
    @Setter
    private Region displayOn;

    public AdminQuestion() {
        this.id = UUID.randomUUID().toString();
    }

    public boolean isActive() {
        return this.inactiveAt == null || this.inactiveAt.before(new Date());
    }
}
