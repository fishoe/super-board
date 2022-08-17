package your.dream.superboard.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.article.data.ArticleGroupMain;
import your.dream.superboard.article.data.ArticleGroupSub;

import java.util.Optional;

public interface ArticleGroupSubRepository extends JpaRepository<ArticleGroupSub, Long> {
    Optional<ArticleGroupSub> findByMainAndCode(ArticleGroupMain main, String code);

    boolean existsByMainAndCode(ArticleGroupMain main, String code);
}