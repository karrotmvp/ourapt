package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value = "Q")
@NoArgsConstructor
public class Question extends Article {

    @Column(name = "main_text")
    @Getter
    private String mainText;

    @Column(name = "pinned_until")
    @Getter
    @Setter
    private Date pinnedUntil;

    public boolean isPinned() {
        return this.pinnedUntil != null && new Date().before(this.pinnedUntil);
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
        this.setUpdatedAt(new Date());
    }

}

