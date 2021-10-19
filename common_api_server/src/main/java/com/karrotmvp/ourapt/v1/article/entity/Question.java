package com.karrotmvp.ourapt.v1.article.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "aritcle_question")
@Entity
@Getter
@Setter
public class Question extends Article {

    @Column(name = "main_text")
    private String mainText;

    @Column(name = "region_id")
    private String regionId;

}
