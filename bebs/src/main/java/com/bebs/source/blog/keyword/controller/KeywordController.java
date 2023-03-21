package com.bebs.source.blog.keyword.controller;


import com.bebs.source.blog.keyword.dto.KeywordDTO;
import com.bebs.source.blog.keyword.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@Tag(name = "keyword", description = "Keyword Controller")
@RequestMapping("/keyword")
public class KeywordController {

    private final KeywordService service;

    @Operation(summary=  "가장 많이 검색된 상위 10개의 Keyword를 조회"
            , description = "가장 많이 검색된 상위 10개의 Keyword를 조회한다.")
    @GetMapping("/popular")
    public List<KeywordDTO> retrievePopularKeywords() {
        return service.retrievePopularKeywords();
    }
}
