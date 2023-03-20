package Source.blogSearch.entity;


import lombok.Getter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "tb_keyword_m")
@Getter
public class BlogSearchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "keyword")
    private String keyword;

}
