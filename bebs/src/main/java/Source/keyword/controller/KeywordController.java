package Source.keyword.controller;


import Source.keyword.dto.KeywordDTO;
import Source.keyword.dto.KeywordRequestDTO;
import Source.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
