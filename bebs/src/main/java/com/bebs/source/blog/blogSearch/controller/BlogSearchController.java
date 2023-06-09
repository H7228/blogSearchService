package com.bebs.source.blog.blogSearch.controller;


import com.bebs.source.blog.blogSearch.dto.BlogSearchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bebs.source.blog.blogSearch.dto.BlogSearchRequestDTO;
import com.bebs.source.blog.blogSearch.service.BlogSearchService;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@Tag(name = "blogSearch", description = "블로그 검색 Controller")
@RequestMapping("/blog/search")
public class BlogSearchController {

    private final BlogSearchService service;

    @Operation(summary=  "키워드로 블로그를 조희"
            , description = "키워드로 블로그를 조회한다. 정확도순, 최신순으로 정렬할 수 있다." +
            "\n[sort] accuracy = 정확도순, recency = 최신순")
    @GetMapping("/keyword")
    public BlogSearchDTO searchBlogByKeyword(BlogSearchRequestDTO request) {
        request.isValidateInput();
        return service.searchBlogByKeyword(request);
    }
}
