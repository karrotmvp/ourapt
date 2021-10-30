package com.karrotmvp.ourapt.v1.adminquestion.entity;


import com.karrotmvp.ourapt.v1.apartment.entity.Region;
import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.common.exception.application.NotFoundInRegionDictException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;
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

    @Column(name = "region_id")
    @Setter
    private String displayOn;

    public AdminQuestion() {
        this.id = UUID.randomUUID().toString();
    }

    public Boolean isActive() {
        Date now = new Date();
        return this.inactiveAt == null || this.inactiveAt.after(now);
    }

    public Region getDisplayOn() {
        return Optional
          .ofNullable(Static.regionDict.get(this.displayOn))
          .orElseThrow(() -> new NotFoundInRegionDictException(this.displayOn));
    }
}
