package com.bebs.source.common.restAPI.service;

import com.bebs.source.common.restAPI.dto.kakao.KakaoBlogSearchDTO;
import com.bebs.source.common.restAPI.dto.kakao.KakaoBlogSearchRequestDTO;
import com.bebs.source.common.excetpion.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
@RequiredArgsConstructor
public class KakaoApiService {

    private final RestTemplate rest;

    @Value("${kakao.rest-api-key}")
    private String REST_API_KEY;

    @Value("${kakao.url.blog-search}")
    private String BLOG_SEARCH_URL;

    public KakaoBlogSearchDTO searchKakaoBlog(KakaoBlogSearchRequestDTO request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + REST_API_KEY);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(BLOG_SEARCH_URL)
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .queryParam("sort", request.getSort());

        HttpEntity<KakaoBlogSearchDTO> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoBlogSearchDTO> response = rest.exchange(
                    uri.build().encode().toUri(),
                    HttpMethod.GET,
                    entity,
                    KakaoBlogSearchDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new ApiException("Kakao Search Blog API 호출에 실패했습니다.");
            }
        } catch (RestClientException e){
            log.error("Kakao API 호출 중 오류 발생", e);
            throw new ApiException("Kakao Search Blog API 호출에 실패했습니다.");
        }
    }
}