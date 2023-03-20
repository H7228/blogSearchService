package Source.blogSearch.dto;

import Source.common.excetpion.BizException;
import Source.common.pagination.PaginationDTO;

import Source.common.restAPI.dto.kakao.KakaoBlogSearchRequestDTO;
import Source.common.restAPI.dto.naver.NaverBlogSearchRequestDTO;
import Source.keyword.dto.KeywordRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
