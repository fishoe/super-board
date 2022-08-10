package your.dream.superboard.users.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.users.user.data.UserPersonal;

import java.util.Optional;

public interface UserPersonalRepository extends JpaRepository<UserPersonal, Long> {
    Optional<UserPersonal> findByUserAuthentication_Username(String username);
}