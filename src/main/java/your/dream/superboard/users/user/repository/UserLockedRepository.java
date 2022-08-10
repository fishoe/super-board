package your.dream.superboard.users.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.users.user.data.UserLocked;
import your.dream.superboard.users.user.data.UserPersonal;

import java.util.Optional;

public interface UserLockedRepository extends JpaRepository<UserLocked, Long> {
    Optional<UserLocked> findFirstByUserOrderByLockedFromDesc(UserPersonal user);
}