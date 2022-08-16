package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "reply_modified_log")
public class ReplyModifiedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reply_id", nullable = false)
    private Reply reply;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "context")
    private String context;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
