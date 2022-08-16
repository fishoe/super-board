package your.dream.superboard.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.article.data.ReplyModifiedLog;

public interface ReplyModifiedLogRepository extends JpaRepository<ReplyModifiedLog, Long> {
}