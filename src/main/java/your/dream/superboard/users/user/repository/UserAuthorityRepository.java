package your.dream.superboard.users.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.users.user.data.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
}