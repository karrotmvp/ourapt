package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.Article;
import com.karrotmvp.ourapt.v1.article.vote.entity.Vote;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue(value = "Q")
public class Question extends Article {

    @Column(name = "main_text")
    @Getter
    private String mainText;

    @Column(name = "pinned_until")
    @Getter
    @Setter
    private Date pinnedUntil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @Setter
    private Vote about;

    public Question() {
        super();
    }

    public boolean isPinned() {
        return this.pinnedUntil != null && new Date().before(this.pinnedUntil);
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
        this.setUpdatedAt(new Date());
    }

}

