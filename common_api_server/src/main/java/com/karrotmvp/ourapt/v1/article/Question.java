package com.karrotmvp.ourapt.v1.article;

import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "aritcle_question")
@Entity
@Getter
public class Question extends Article {

    @Id
    private String id;

    @Column(name = "main_text")
    private String mainText;

    public Question() {
        this.id = UUID.randomUUID().toString();
    }
}
