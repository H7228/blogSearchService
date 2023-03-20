package Source.common.restAPI.dto.naver;


import Source.common.pagination.PaginationDTO;
import lombok.Data;

@Data
public class NaverBlogSearchRequestDTO extends PaginationDTO {
    private String query;
}
