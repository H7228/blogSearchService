package com.bebs.source.blog.keyword.controller;


import com.bebs.source.blog.keyword.dto.KeywordDTO;
import com.bebs.source.blog.keyword.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "keyword", description = "Keyword Controller")
@RequestMapping("/keyword")
public class KeywordController {

    private final KeywordService service;


    @Operation(summary=  "키워드로 블로그를 조희"
            , description = "키워드로 블로그를 조회한다. 정확도순, 최신순으로 정렬할 수 있다.")
    @GetMapping("/popular")
    public List<KeywordDTO> retrievePopularKeywords() {
        return service.retrievePopularKeywords();
    }

}
