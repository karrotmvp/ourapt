package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue(value = "Q")
@NoArgsConstructor
public class Question extends Article {

    @Column(name = "main_text")
    @Getter
    @Setter
    private String mainText;

    @Column(name = "is_pinned")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date pinnedUntil;

    public Boolean isPinned() {
        return this.pinnedUntil != null && new Date().before(this.pinnedUntil);
    }
}

