package com.bebs.source.blog.keyword.service;

import com.bebs.source.blog.keyword.dto.KeywordDTO;
import com.bebs.source.blog.keyword.dto.KeywordRequestDTO;
import com.bebs.source.blog.keyword.entity.KeywordEntity;
import com.bebs.source.blog.keyword.repository.KeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeywordServiceTest {

    @InjectMocks
    private KeywordService keywordService;

    @Mock
    private KeywordRepository keywordRepository;

    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        keywordService = new KeywordService(new ModelMapper(), keywordRepository);
        mapper = new ModelMapper();
    }

    @Test
    @DisplayName("인기 검색어 조회")
    void retrievePopularKeywordsTest() {
        List<KeywordEntity> savedEntities = List.of(
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "kakao", 10000L),
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "bank", 140L),
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "old", 130L),
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "new", 120L),
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "01", 1520L),
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "02", 120L),
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "03", 110L),
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "test", 154L),
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "case", 251L),
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "keyword", 1L),
                new KeywordEntity(String.valueOf(UUID.randomUUID()), "04", 2L)
        );
        when(keywordRepository.saveAll(savedEntities)).thenReturn(savedEntities);

        List<KeywordDTO> result = keywordService.retrievePopularKeywords();

        assertThat(result).hasSize(10);
        assertThat(result.get(0).getKeyword()).isEqualTo("kakao");
        assertThat(result.get(0).getCount()).isEqualTo(10000L);
    }

    @Test
    @DisplayName("검색어 저장 - 존재하는 검색어")
    void saveOrUpdateKeyword_whenKeywordExists() {
        //given
        KeywordRequestDTO request = KeywordRequestDTO.builder()
                .keyword("test")
                .build();

        KeywordEntity existEntity = new KeywordEntity(String.valueOf(UUID.randomUUID()), "test", 3L);

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
