package your.dream.superboard.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.article.data.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}