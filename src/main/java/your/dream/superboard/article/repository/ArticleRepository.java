package your.dream.superboard.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.article.data.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}