package com.bebs.source.blog.keyword.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_keyword_m")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "count")
    private Long count;

}
