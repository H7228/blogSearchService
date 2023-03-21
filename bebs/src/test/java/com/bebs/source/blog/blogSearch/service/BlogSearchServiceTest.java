package com.bebs.source.blog.blogSearch.service;

import com.bebs.source.blog.blogSearch.dto.BlogSearchDTO;
import com.bebs.source.blog.blogSearch.dto.BlogSearchRequestDTO;
import com.bebs.source.blog.keyword.service.KeywordService;
import com.bebs.source.common.excetpion.ApiException;
import com.bebs.source.common.restAPI.dto.kakao.KakaoBlogSearchDTO;
import com.bebs.source.common.restAPI.dto.naver.NaverBlogSearchDTO;
import com.bebs.source.common.restAPI.service.KakaoApiService;
import com.bebs.source.common.restAPI.service.NaverApiService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogSearchServiceTest {

    private BlogSearchService blogSearchService;

    @Mock
    private ModelMapper mapper;

    @Mock
    private KeywordService keywordService;

    @Mock
    private KakaoApiService kakaoApiService;

    @Mock
    private NaverApiService naverApiService;

    @BeforeEach
    void setUp() {
        blogSearchService = new BlogSearchService(mapper, keywordService, kakaoApiService, naverApiService);
    }

    @Nested
    @DisplayName("searchBlogByKeyword 메서드")
    class SearchBlogByKeyword {

        @Test
        @DisplayName("정상적으로 검색 결과를 반환하는 경우 - kakao")
        void searchBlogByKeywordKakao_returnsResult() {
            // given
            BlogSearchRequestDTO request = new BlogSearchRequestDTO();
            request.setKeyword("test");
            BlogSearchDTO expectedResult = new BlogSearchDTO();

            // when
            when(kakaoApiService.searchKakaoBlog(any())).thenReturn(new KakaoBlogSearchDTO());
            when(mapper.map(any(), any())).thenReturn(expectedResult);

            BlogSearchDTO result = blogSearchService.searchBlogByKeyword(request);

            // then
            assertNotNull(result);
            assertEquals(expectedResult, result);
            verify(keywordService, times(1)).saveOrUpdateKeyword(any());
            verify(kakaoApiService, times(1)).searchKakaoBlog(any());
        }

        @Test
        @DisplayName("정상적으로 검색 결과를 반환하는 경우 - naver")
        void searchBlogByKeywordNaver_returnsResult() {
            // given
            BlogSearchRequestDTO request = new BlogSearchRequestDTO();
            request.setKeyword("test");
            BlogSearchDTO expectedResult = new BlogSearchDTO();

            // when
            when(kakaoApiService.searchKakaoBlog(any())).thenThrow(ApiException.class);
            when(naverApiService.searchNaverBlog(any())).thenReturn(null);

            BlogSearchDTO result = blogSearchService.searchBlogByKeyword(request);

            // then
            assertNotNull(result);
            assertEquals(expectedResult, result);
            verify(keywordService, times(1)).saveOrUpdateKeyword(any());
            verify(naverApiService, times(1)).searchNaverBlog(any());
        }

        @Test
        @DisplayName("API 호출이 모두 실패하는 경우 ApiException이 발생")
        void searchBlogByKeyword_throwsApiException_whenAllApiFail() {
            // given
            BlogSearchRequestDTO request = new BlogSearchRequestDTO();
            request.setKeyword("test");

            // when
            when(kakaoApiService.searchKakaoBlog(any())).thenThrow(ApiException.class);
            when(naverApiService.searchNaverBlog(any())).thenThrow(ApiException.class);

            // then
            assertThrows(ApiException.class, () -> blogSearchService.searchBlogByKeyword(request));
            verify(keywordService, times(1)).saveOrUpdateKeyword(any());
            verify(kakaoApiService, times(1)).searchKakaoBlog(any());
            verify(naverApiService, times(1)).searchNaverBlog(any());
        }
    }
}
