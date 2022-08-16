package your.dream.superboard.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.article.data.ArticleViewLog;

public interface ArticleViewLogRepository extends JpaRepository<ArticleViewLog, Long> {
}