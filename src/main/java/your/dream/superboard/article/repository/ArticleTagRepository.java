package your.dream.superboard.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.article.data.ArticleTag;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
}