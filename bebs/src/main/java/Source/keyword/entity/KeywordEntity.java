package Source.keyword.entity;


import lombok.Getter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "tb_keyword_m")
@Getter
public class KeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "count")
    private Long count;

}
