package com.bebs.source.blog.keyword.service;

import com.bebs.source.blog.keyword.dto.KeywordDTO;
import com.bebs.source.blog.keyword.dto.KeywordRequestDTO;
import com.bebs.source.blog.keyword.entity.KeywordEntity;
import com.bebs.source.blog.keyword.repository.KeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
class KeywordServiceTest {

    @Autowired
    private KeywordService keywordService;

    @MockBean
    private KeywordRepository keywordRepository;

    private ModelMapper mapper;


    @BeforeEach
    void setUp() {
        mapper = new ModelMapper();
    }

    @Test
    @DisplayName("인기 검색어 조회")
    void retrievePopularKeywordsTest() {

        // given
        List<KeywordEntity> savedEntities = List.of(
                new KeywordEntity("1", "kakao", 10000L),
                new KeywordEntity("2", "bank", 140L),
                new KeywordEntity("3", "old", 130L),
                new KeywordEntity("4", "new", 120L),
                new KeywordEntity("5", "01", 1520L),
                new KeywordEntity("6", "02", 120L),
                new KeywordEntity("7", "03", 110L),
                new KeywordEntity("8", "test", 154L),
                new KeywordEntity("9", "case", 251L),
                new KeywordEntity("10", "keyword", 1L),
                new KeywordEntity("11", "04", 2L));

        // when
        Mockito.when(keywordRepository.findTop10ByOrderByCountDesc()).thenReturn(savedEntities);
        List<KeywordDTO> actualKeywords = keywordService.retrievePopularKeywords();

        // then
        assertEquals(actualKeywords.size(), 10);

        for (int i = 0; i < 10; i++) {
            KeywordEntity expectedEntity = savedEntities.get(i);
            KeywordDTO actualDTO = actualKeywords.get(i);

            assertEquals(expectedEntity.getId(), actualDTO.getId());
            assertEquals(expectedEntity.getKeyword(), actualDTO.getKeyword());
            assertEquals(expectedEntity.getCount(), actualDTO.getCount());
        }

    }

    @Test
    @DisplayName("인기 검색어가 없는 경우")
    void retrievePopularKeywords_whenEmpty_returnsEmptyList() {
        when(keywordRepository.findTop10ByOrderByCountDesc()).thenReturn(Collections.emptyList());

        List<KeywordDTO> result = keywordService.retrievePopularKeywords();

        assertThat(result).isEmpty();
    }



    @Test
    @DisplayName("검색어 저장 - 존재하는 검색어")
    void saveOrUpdateKeyword_whenKeywordExists() {
        //given
        KeywordRequestDTO request = KeywordRequestDTO.builder()
                .keyword("test")
                .build();

        KeywordEntity existEntity = new KeywordEntity("2", "test", 3L);

        when(keywordRepository.findByKeyword(request.getKeyword())).thenReturn(Optional.of(existEntity));
        when(keywordRepository.save(existEntity)).thenReturn(existEntity);

        //when
        keywordService.saveOrUpdateKeyword(request);

        //then
        verify(keywordRepository, times(1)).save(mapper.map(existEntity, KeywordEntity.class));
        assertThat(existEntity.getCount()).isEqualTo(4L);
    }

    @Test
    @DisplayName("검색어 저장 - 존재하지 않는 검색어")
    void saveOrUpdateKeyword_whenKeywordDoesNotExist() {
        //given
        KeywordRequestDTO request = new KeywordRequestDTO("test");
        when(keywordRepository.findByKeyword(request.getKeyword()))
                .thenReturn(Optional.empty());

        //when
        keywordService.saveOrUpdateKeyword(request);

        //then
        verify(keywordRepository, times(1)).save(any(KeywordEntity.class));
    }
}
