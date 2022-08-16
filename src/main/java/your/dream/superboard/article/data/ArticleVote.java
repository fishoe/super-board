package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.Setter;
import your.dream.superboard.users.user.data.UserPersonal;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "article_vote", uniqueConstraints = {
        @UniqueConstraint(name = "uc_article_vote_user_article", columnNames = {"user_id", "article_id"})
})
public class ArticleVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserPersonal user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "vote_up", nullable = false)
    private boolean voteUp;

}