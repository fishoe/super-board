package your.dream.superboard.users.user.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import your.dream.superboard.authentication.password.encoder.PasswordEncoderStorage;
import your.dream.superboard.users.user.request.UserRequest;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user_authentication", indexes = {
        @Index(name = "idx_uniq_username", columnList = "username"),
        @Index(name = "idx_uniq_personal", columnList = "personal_id")
})
@NoArgsConstructor
public class UserAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 30, unique = true)
    private String username;
    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "personal_id", nullable = false, unique = true)
    private UserPersonal personal;

    @Column(name = "last_password_changed", nullable = false)
    private LocalDate lastPasswordChanged;

    public UserAuthentication(UserRequest userRequest, UserPersonal personal, PasswordEncoderStorage pwEncoder){
        username = userRequest.getUsername();
        password = pwEncoder.getPasswordEncoder().encode(userRequest.getPassword());
        this.personal = personal;
        lastPasswordChanged = personal.getCreatedAt().toLocalDate();
    }
}