package Source.common.restAPI.dto.kakao;


import Source.common.pagination.PaginationDTO;
import lombok.Data;

@Data
public class KakaoBlogSearchRequestDTO extends PaginationDTO {
    private String query;
}
