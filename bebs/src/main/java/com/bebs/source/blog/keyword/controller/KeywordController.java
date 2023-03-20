package com.bebs.source.blog.keyword.controller;


import com.bebs.source.blog.keyword.dto.KeywordDTO;
import com.bebs.source.blog.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keyword")
public class KeywordController {

    private final KeywordService service;

    @GetMapping("/popular")
    public List<KeywordDTO> retrievePopularKeywords() {
        return service.retrievePopularKeywords();
    }

}
