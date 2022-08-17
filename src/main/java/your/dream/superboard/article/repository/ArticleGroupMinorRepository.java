package your.dream.superboard.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.article.data.ArticleGroupMajor;
import your.dream.superboard.article.data.ArticleGroupMinor;

import java.util.Optional;

public interface ArticleGroupMinorRepository extends JpaRepository<ArticleGroupMinor, Long> {
    Optional<ArticleGroupMinor> findByMajorAndCode(ArticleGroupMajor major, String code);
}