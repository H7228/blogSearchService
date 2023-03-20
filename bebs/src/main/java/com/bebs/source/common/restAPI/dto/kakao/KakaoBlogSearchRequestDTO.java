package com.bebs.source.common.restAPI.dto.kakao;


import lombok.EqualsAndHashCode;
import com.bebs.source.common.pagination.PaginationDTO;
import lombok.Data;

@Data
@EqualsAndHashCode(callSuper=false)
public class KakaoBlogSearchRequestDTO extends PaginationDTO {
    private String query;
}
