package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.Article;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Q")
public class Question extends Article {
    public Question() {
        super();
    }
}

