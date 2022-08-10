package your.dream.superboard.users.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.users.user.data.UserAuthentication;

import java.util.Optional;

public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Long> {
    Optional<UserAuthentication> findByUsername(String username);
}