package com.bebs.source.common.restAPI.dto.naver;

import com.bebs.source.blog.blogSearch.dto.BlogSearchDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverBlogSearchDTO {

    private List<Item> items;

    private int start;
    private int display;
    private int total;
    private String lastBuildDate;

    @Data
    public static class Item {
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;
        private String postdate;
    }

    public BlogSearchDTO toBlogSearchDTO(){
        boolean isEnd = (this.start + this.display - 1) / this.display == this.total / this.display;

        List<BlogSearchDTO.Document> itemList = items.stream().map(e -> BlogSearchDTO.Document.builder()
                .title(e.getTitle())
                .contents(e.getDescription())
                .url(e.getBloggerlink())
                .blogname(e.getBloggername())
                .datetime(e.getPostdate())
                .build()).collect(Collectors.toList());

        return BlogSearchDTO.builder()
                .meta(BlogSearchDTO.Meta.builder()
                        .isEnd(isEnd)
                        .pageableCount(this.display)
                        .totalCount(this.total)
                        .build())
                .documents(itemList)
                .build();
    }


}
