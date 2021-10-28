package com.karrotmvp.ourapt.v1.notification;


import com.karrotmvp.ourapt.v1.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "notification")
@Entity
@DiscriminatorValue(value = "K")
@Getter
public class KarrotNotification extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "main_text")
    private String mainText;

    public KarrotNotification() {
        this.id = UUID.randomUUID().toString();
    }

}
