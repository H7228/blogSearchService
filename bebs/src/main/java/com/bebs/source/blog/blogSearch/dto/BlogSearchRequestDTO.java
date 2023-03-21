package com.bebs.source.blog.blogSearch.dto;

import com.bebs.source.common.constant.Constant;
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

    public void isValidateInput(){
        checkKeyword();
        checkSortType();
    }

    private void checkKeyword() {
        if (StringUtils.isBlank(this.keyword)) {
            throw new BizException("검색어가 존재하지 않습니다");
        }
    }

    private void checkSortType() {
        if (StringUtils.isNotBlank(this.getSort()) && !isInvalidSortType()) {
            throw new BizException("잘못된 정렬 TYPE이 입력되었습니다.");
        }
    }

    private boolean isInvalidSortType() {
        return !Constant.SortTypeKakao.ACCURACY.equals(this.getSort()) && !Constant.SortTypeKakao.RECENCY.equals(this.getSort());
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
        mapDTO.setSort(convertNaverSortType(mapDTO.getSort()));
        mapDTO.setQuery(this.keyword);
        return mapDTO;
    }

    public String convertNaverSortType(String sort) {
        if (Constant.SortTypeKakao.ACCURACY.getCode().equalsIgnoreCase(sort)) {
            return Constant.SortTypeNaver.SIM.getCode();
        } else if (Constant.SortTypeKakao.RECENCY.getCode().equalsIgnoreCase(sort)) {
            return Constant.SortTypeNaver.DATE.getCode();
        } else {
            throw new IllegalArgumentException("잘못된 정렬 type: " + sort);
        }
    }

    public KeywordRequestDTO toKeywordReqeustDTO(){
        return KeywordRequestDTO.builder().keyword(this.keyword).build();
    }

}
