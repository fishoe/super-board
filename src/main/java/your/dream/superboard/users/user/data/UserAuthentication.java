package your.dream.superboard.users.user.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user_authentication", indexes = {
        @Index(name = "idx_uniq_username", columnList = "username"),
        @Index(name = "idx_uniq_personal", columnList = "personal_id")
})
public class UserAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 30, unique = true)
    private String username;
    @Column(name = "password", nullable = false, length = 32)
    private String password;


    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "personal_id", nullable = false, unique = true)
    private UserPersonal personal;

    @Column(name = "last_password_changed", nullable = false)
    private LocalDate lastPasswordChanged;

}