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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
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
        when(keywordRepository.findAll()).thenReturn(savedEntities);
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
