package com.bebs.source.common.restAPI.service;

import com.bebs.source.common.excetpion.ApiException;
import com.bebs.source.common.restAPI.dto.naver.NaverBlogSearchDTO;
import com.bebs.source.common.restAPI.dto.naver.NaverBlogSearchRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;

@Service
@Log4j2
@RequiredArgsConstructor
public class NaverApiService {

    private final RestTemplate rest;

    @Value("${naver.client-id}")
    private String CLIENT_ID;

    @Value("${naver.client-secret}")
    private String CLIENT_SECRET;

    @Value("${naver.url.blog-search}")
    private String BLOG_SEARCH_URL;

    public NaverBlogSearchDTO searchNaverBlog(NaverBlogSearchRequestDTO request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(BLOG_SEARCH_URL)
                .queryParam("query", request.getQuery())
                .queryParam("start", request.getPage())
                .queryParam("display", request.getSize())
                .queryParam("sort", request.getSort());

        HttpEntity<NaverBlogSearchDTO> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<NaverBlogSearchDTO> response = rest.exchange(
                    uri.build().encode().toUri(),
                    HttpMethod.GET,
                    entity,
                    NaverBlogSearchDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new ApiException("Naver Search Blog API 호출에 실패했습니다.");
            }
        } catch (RestClientException e){
            log.error("Naver API 호출 중 오류 발생", e);
            throw new ApiException("Naver Search Blog API 호출에 실패했습니다.");
        }
    }

}