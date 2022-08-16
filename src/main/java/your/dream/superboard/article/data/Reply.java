package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.Setter;
import your.dream.superboard.users.user.data.UserPersonal;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private UserPersonal author;

    @Column(name = "author_name", nullable = false, length = 40)
    private String authorName;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "last_modified_at")
    private Instant lastModifiedAt;

    @Lob
    @Column(name = "context", nullable = false)
    private String context;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "reply", orphanRemoval = true)
    private Set<ReplyVote> votes = new LinkedHashSet<>();

}