package com.bebs.source.common.pagination;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PaginationDTO {

    private String sort = "accuracy";
    private String page = "1";
    private String size = "10";

}
