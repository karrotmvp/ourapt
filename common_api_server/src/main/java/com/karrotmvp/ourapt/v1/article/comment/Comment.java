package com.karrotmvp.ourapt.v1.article.comment;


import com.karrotmvp.ourapt.v1.article.Article;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "C")
public class Comment extends Article {

    @Column(name = "main_text")
    @Getter
    @Setter
    private String mainText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @Setter
    private Article parent;
}
