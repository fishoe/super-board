package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "article_modified_log")
public class ArticleModifiedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "subject", length = 120)
    private String subject;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "context")
    private String context;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "group_sub_id")
    private ArticleGroupSub groupSub;

}