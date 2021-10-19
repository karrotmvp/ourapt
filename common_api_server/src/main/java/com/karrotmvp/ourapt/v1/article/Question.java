package com.karrotmvp.ourapt.v1.article;

import javax.persistence.*;

@Table(name = "question")
public class Question extends Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "main_text")
    private String mainText;
}
