package Source.common.restAPI.dto.naver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverBlogSearchDTO {

    private Meta meta;
    private List<Document> documents;

    @Data
    public static class Meta {
        private int start;
        private int display;
        private int total;
        private LocalDateTime lastBuildDate;
    }

    @Data
    public static class Document {
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;
        private LocalDateTime postdate;
    }


}
