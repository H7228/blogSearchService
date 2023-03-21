package com.bebs.source.blog.keyword.service;

import com.bebs.source.blog.keyword.dto.KeywordDTO;
import com.bebs.source.blog.keyword.dto.KeywordRequestDTO;
import com.bebs.source.blog.keyword.entity.KeywordEntity;
import com.bebs.source.blog.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    private final ModelMapper mapper;
    private final KeywordRepository repository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveOrUpdateKeyword(KeywordRequestDTO request) {
        Optional<KeywordEntity> optional = repository.findByKeyword(request.getKeyword());
        if(optional.isPresent()){
            updateKeywordCount(optional.get());
        }else {
            saveInitKeyword(request.getKeyword());
        }
    }

    private void updateKeywordCount(KeywordEntity keywordEntity) {
        keywordEntity.incrementCount();
        repository.save(keywordEntity);
    }

    private void saveInitKeyword(String keyword) {
        KeywordDTO keywordDTO =  KeywordDTO.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .keyword(keyword)
                .count(1L)
                .build();
        repository.save(mapper.map(keywordDTO, KeywordEntity.class));
    }

    @Transactional(readOnly = true)
    public List<KeywordDTO> retrievePopularKeywords() {
        List<KeywordEntity> entities = repository.findTop10ByOrderByCountDesc();
        return entities.stream()
                .map(e -> KeywordDTO.builder()
                        .id(e.getId())
                        .keyword(e.getKeyword())
                        .count(e.getCount())
                        .build())
                .collect(Collectors.toList());
    }
}
