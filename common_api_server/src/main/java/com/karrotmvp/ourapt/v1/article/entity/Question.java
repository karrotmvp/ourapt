package com.karrotmvp.ourapt.v1.article.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "Q")
public class Question extends Article {
    @Column(name = "main_text")
    private String mainText;
}
