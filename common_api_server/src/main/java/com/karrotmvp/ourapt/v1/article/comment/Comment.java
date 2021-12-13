package com.karrotmvp.ourapt.v1.article.comment;


import com.karrotmvp.ourapt.v1.article.Article;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "C")
public class Comment extends Article {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @Setter
    @Getter
    private Article parent;
}
