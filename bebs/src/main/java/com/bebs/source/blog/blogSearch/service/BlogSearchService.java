package com.bebs.source.blog.blogSearch.service;

import com.bebs.source.blog.blogSearch.dto.BlogSearchDTO;
import com.bebs.source.common.restAPI.service.NaverApiService;
import com.bebs.source.blog.keyword.service.KeywordService;
import com.bebs.source.blog.blogSearch.dto.BlogSearchRequestDTO;
import com.bebs.source.common.excetpion.ApiException;
import com.bebs.source.common.restAPI.dto.kakao.KakaoBlogSearchDTO;
import com.bebs.source.common.restAPI.dto.naver.NaverBlogSearchDTO;
import com.bebs.source.common.restAPI.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogSearchService {

    private final ModelMapper mapper;
    private final KeywordService keywordService;

    private final KakaoApiService kakaoApiService;
    private final NaverApiService naverApiService;

    public BlogSearchDTO searchBlogByKeyword(BlogSearchRequestDTO request) {

        //1. Keyword save
        keywordService.saveOrUpdateKeyword(request.toKeywordReqeustDTO());

        //2. search API
        BlogSearchDTO result = this.searchBlogApi(request);

        return result;
    }


    @Cacheable(cacheNames = "searchBlog", key = "#request.keyword")
    private BlogSearchDTO searchBlogApi(BlogSearchRequestDTO request) {
        BlogSearchDTO result = this.searchKakaoBlog(request);
        if (result != null) {
            return result;
        }
        result = this.searchNaverBlog(request);
        if (result != null) {
            return result;
        }

        throw new ApiException("모든 블로그 검색 API 호출에 실패하였습니다.");
    }

    private BlogSearchDTO searchKakaoBlog(BlogSearchRequestDTO request) {
        try {
            KakaoBlogSearchDTO searchDTO = kakaoApiService.searchKakaoBlog(
                    request.toKakaoBlogSearchRequestDTO());
            return mapper.map(searchDTO, BlogSearchDTO.class);
        } catch (ApiException e) {
            return null;
        }
    }

    private BlogSearchDTO searchNaverBlog(BlogSearchRequestDTO request) {
        try {
            NaverBlogSearchDTO searchDTO = naverApiService.searchNaverBlog(
                    request.toNaverBlogSearchRequestDTO());
            if(searchDTO == null) return new BlogSearchDTO();
            return searchDTO.toBlogSearchDTO();
        } catch (ApiException e) {
            return null;
        }
    }

}
