package your.dream.superboard.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.article.data.ArticleGroupMain;

import java.util.Optional;

public interface ArticleGroupMainRepository extends JpaRepository<ArticleGroupMain, Long> {
    Optional<ArticleGroupMain> findByPath(String path);

    boolean existsByPath(String path);
}