package your.dream.superboard.users.user.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user_locked")
public class UserLocked {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "message", nullable = false, length = 100)
    private String message;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserPersonal user;

    @Column(name = "locked_from")
    private Instant lockedFrom;

    @Column(name = "locked_to")
    private Instant lockedTo;
}