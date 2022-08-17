package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "article_view_log", uniqueConstraints = {
        @UniqueConstraint(name = "uc_article_view_log_article_session", columnNames = {"article_id", "session"})
})
public class ArticleViewLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "session", nullable = false, length = 64)
    private String session;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}