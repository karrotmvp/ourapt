package com.karrotmvp.ourapt.v1.notification;


import com.karrotmvp.ourapt.v1.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "notification")
@Entity
@DiscriminatorValue(value = "K")
public class Notification extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @Getter
    private String id;

    @Column(name = "main_text")
    @Getter
    @Setter
    private String mainText;

    public Notification() {
        this.id = UUID.randomUUID().toString();
    }

}
