package com.bebs.source.common.restAPI.dto.naver;


import com.bebs.source.common.pagination.PaginationDTO;
import lombok.EqualsAndHashCode;
import lombok.Data;

@Data
@EqualsAndHashCode(callSuper=false)
public class NaverBlogSearchRequestDTO extends PaginationDTO {
    private String query;
}
