package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Q")
@NoArgsConstructor
public class Question extends Article {

    @Column(name = "main_text")
    @Getter
    @Setter
    private String mainText;
}
