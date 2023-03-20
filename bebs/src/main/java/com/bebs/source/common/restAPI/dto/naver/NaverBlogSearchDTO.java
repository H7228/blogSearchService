package com.bebs.source.common.restAPI.dto.naver;

import com.bebs.source.blog.blogSearch.dto.BlogSearchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverBlogSearchDTO {

    private Meta meta;
    private List<Document> documents;

    @Data
    public static class Meta {
        private int start;
        private int display;
        private int total;
        private LocalDateTime lastBuildDate;
    }

    @Data
    public static class Document {
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;
        private LocalDateTime postdate;
    }

    public BlogSearchDTO toBlogSearchDTO(){
        boolean isEnd = (meta.getStart() + meta.getDisplay() - 1) / meta.getDisplay() == meta.getTotal() / meta.getDisplay();

        List<BlogSearchDTO.Document> documentList = documents.stream().map(e -> BlogSearchDTO.Document.builder()
                .title(e.getTitle())
                .contents(e.getDescription())
                .url(e.getBloggerlink())
                .blogname(e.getBloggername())
                .datetime(e.getPostdate())
                .build()).collect(Collectors.toList());

        return BlogSearchDTO.builder()
                .meta(BlogSearchDTO.Meta.builder()
                        .isEnd(isEnd)
                        .pageableCount(meta.getDisplay())
                        .totalCount(meta.getTotal())
                        .build())
                .documents(documentList)
                .build();
    }


}
