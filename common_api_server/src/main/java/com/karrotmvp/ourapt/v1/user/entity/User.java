package com.karrotmvp.ourapt.v1.user.entity;

import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {

    @Id
    @Column(name = "karrot_id", nullable = false)
    @Getter
    private String id;

    @Transient
    @Getter
    @Setter
    private KarrotProfile profile;

    @Column(name = "push_agreed_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date pushAgreedAt;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private Date deletedAt;

    @Column(name = "banned_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    @Getter
    private Date bannedAt;

    @Column(name = "is_admin")
    @Getter
    @Setter
    private boolean isAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checked_in")
    @Getter
    @Setter
    private Apartment checkedIn;


    public boolean isDeleted() {
        return this.deletedAt != null && deletedAt.before(new Date());
    }

    public boolean isPushAgreed() {
        return this.pushAgreedAt != null && new Date().after(this.pushAgreedAt);
    }

    public User(String userId, KarrotProfile profile) {
        this.id = userId;
        this.profile = profile;
        this.isAdmin = false;
    }

    public User(String userId, KarrotProfile profile, boolean isAdmin) {
        // TODO: 관리자의 경우 상속을 이용하도록 리팩토링
        this.id = userId;
        this.profile = profile;
        this.isAdmin = isAdmin;
    }
}

