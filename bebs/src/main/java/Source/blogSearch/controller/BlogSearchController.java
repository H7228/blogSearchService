package Source.blogSearch.controller;


import Source.blogSearch.dto.BlogSearchDTO;
import Source.blogSearch.dto.BlogSearchRequestDTO;
import Source.blogSearch.service.BlogSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogSearchController {

    private final BlogSearchService service;

    @GetMapping("/search/keyword")
    public BlogSearchDTO searchBlogByKeyword(@RequestParam BlogSearchRequestDTO request) {
        request.isVerification();
        return service.searchBlogByKeyword(request);
    }
}
