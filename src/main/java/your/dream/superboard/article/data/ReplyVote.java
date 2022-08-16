package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.Setter;
import your.dream.superboard.users.user.data.UserPersonal;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "reply_vote", uniqueConstraints = {
        @UniqueConstraint(name = "uc_reply_vote_user_reply", columnNames = {"user_id", "reply_id"})
})
public class ReplyVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserPersonal user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reply_id", nullable = false)
    private Reply reply;

    @Column(name = "vote_up", nullable = false)
    private boolean voteUp;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}