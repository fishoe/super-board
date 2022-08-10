package your.dream.superboard.users.user.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user_personal")
public class UserPersonal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @OneToOne(mappedBy = "personal", orphanRemoval = true)
    private UserAuthentication userAuthentication;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<UserLocked> userLockedLog = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<UserAuthority> authorities = new LinkedHashSet<>();

}