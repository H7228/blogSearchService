package Source.keyword.repository;


import Source.keyword.entity.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<KeywordEntity, String> {
    Optional<KeywordEntity> findByKeyword(String keyword);
    List<KeywordEntity> findTop10ByOrderByCountDesc();
}
