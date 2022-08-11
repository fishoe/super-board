package your.dream.superboard.users.user.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import your.dream.superboard.users.user.request.UserRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user_personal")
@NoArgsConstructor
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

    @OneToOne(mappedBy = "personal", cascade = {CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true)
    private UserAuthentication userAuthentication;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<UserLocked> userLockedLog = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<UserAuthority> authorities = new LinkedHashSet<>();

    public UserPersonal(UserRequest userRequest){
        name = userRequest.getName();
        createdAt = LocalDateTime.now();
        deleted = false;
    }
}