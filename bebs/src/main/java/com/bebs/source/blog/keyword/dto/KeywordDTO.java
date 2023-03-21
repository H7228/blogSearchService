package com.bebs.source.blog.keyword.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeywordDTO {
    private String id;
    private String keyword;
    private Long count;
}
