package Source.blogSearch.repository;


import Source.blogSearch.entity.BlogSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogSearchRepository extends JpaRepository<BlogSearchEntity, String> {
    public Optional<BlogSearchEntity> findByKeyword(String keyword);
}
