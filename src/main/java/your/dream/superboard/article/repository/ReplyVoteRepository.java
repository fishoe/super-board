package your.dream.superboard.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.article.data.ReplyVote;

public interface ReplyVoteRepository extends JpaRepository<ReplyVote, Long> {
}