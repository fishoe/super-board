package your.dream.superboard.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import your.dream.superboard.authentication.data.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("update RefreshToken r set r.enabled = ?1 where r.subject = ?2")
    int updateEnabledBySubject(Boolean enabled, String subject);

    boolean existsByToken(String token);
}