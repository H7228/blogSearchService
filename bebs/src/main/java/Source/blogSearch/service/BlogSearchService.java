package Source.blogSearch.service;

import Source.blogSearch.dto.BlogSearchDTO;
import Source.blogSearch.dto.BlogSearchRequestDTO;
import Source.common.excetpion.ApiException;
import Source.common.excetpion.BizException;
import Source.common.restAPI.dto.kakao.KakaoBlogSearchDTO;
import Source.common.restAPI.dto.naver.NaverBlogSearchDTO;
import Source.common.restAPI.service.KakaoApiService;
import Source.common.restAPI.service.NaverApiService;
import Source.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogSearchService {

    private final ModelMapper mapper;
    private final KeywordService keywordService;

    private final KakaoApiService kakaoApiService;
    private final NaverApiService naverApiService;

    public BlogSearchDTO searchBlogByKeyword(BlogSearchRequestDTO request) {

        //TODO: 두 작업을 비동기로 처리
        //1. Keyword save
        keywordService.saveOrUpdateKeyword(request.toKeywordReqeustDTO());

        //TODO: 조회결과가 없는 경우랑 API 호출에 실패한 경우를 나눠야 함
        //2. search API
        BlogSearchDTO result = this.getBlogSearchDTO(request);

        return result;
    }

    private BlogSearchDTO getBlogSearchDTO(BlogSearchRequestDTO request) {
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
            return mapper.map(searchDTO, BlogSearchDTO.class);
        } catch (ApiException e) {
            return null;
        }
    }

}
