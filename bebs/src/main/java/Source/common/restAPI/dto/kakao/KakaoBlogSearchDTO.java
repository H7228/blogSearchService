package Source.common.restAPI.dto.kakao;

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
public class KakaoBlogSearchDTO{

    private Meta meta;
    private List<Document> documents;

    @Data
    public static class Meta {
        private boolean isEnd;
        private int pageableCount;
        private int totalCount;
    }

    @Data
    public static class Document {
        private String title;
        private String contents;
        private String url;
        private String blogname;
        private String thumbnail;
        private LocalDateTime datetime;
    }


}
