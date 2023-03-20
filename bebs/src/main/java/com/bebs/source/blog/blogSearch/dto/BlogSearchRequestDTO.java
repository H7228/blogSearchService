package com.bebs.source.blog.blogSearch.dto;

import com.bebs.source.common.excetpion.BizException;
import com.bebs.source.common.restAPI.dto.kakao.KakaoBlogSearchRequestDTO;
import com.bebs.source.blog.keyword.dto.KeywordRequestDTO;
import lombok.*;
import com.bebs.source.common.pagination.PaginationDTO;

import com.bebs.source.common.restAPI.dto.naver.NaverBlogSearchRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class BlogSearchRequestDTO extends PaginationDTO {

    private String keyword;

    public boolean isVerification(){
        if(StringUtils.isEmpty(this.keyword)){
            throw new BizException("검색어가 존재하지 않습니다");
        }
        return true;
    }

    public KakaoBlogSearchRequestDTO toKakaoBlogSearchRequestDTO(){
        ModelMapper mapper = new ModelMapper();
        KakaoBlogSearchRequestDTO mapDTO = mapper.map(this, KakaoBlogSearchRequestDTO.class);
        mapDTO.setQuery(this.keyword);
        return mapDTO;
    }

    public NaverBlogSearchRequestDTO toNaverBlogSearchRequestDTO(){
        ModelMapper mapper = new ModelMapper();
        NaverBlogSearchRequestDTO mapDTO = mapper.map(this, NaverBlogSearchRequestDTO.class);
        mapDTO.setQuery(this.keyword);
        return mapDTO;
    }


    public KeywordRequestDTO toKeywordReqeustDTO(){
        return KeywordRequestDTO.builder().keyword(this.keyword).build();
    }

}
