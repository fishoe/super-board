package your.dream.superboard.users.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import your.dream.superboard.users.user.authentication.StoredUserCredit;
import your.dream.superboard.users.user.data.UserAuthentication;
import your.dream.superboard.users.user.data.UserLocked;
import your.dream.superboard.users.user.repository.UserAuthenticationRepository;
import your.dream.superboard.users.user.repository.UserLockedRepository;
import your.dream.superboard.users.user.repository.UserPersonalRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserAuthenticationRepository userAuthenticationRepository;
    private final UserLockedRepository userLockedRepository;
    private final UserPersonalRepository userPersonalRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserAuthentication user = userAuthenticationRepository.findByUsername(username)
                    .orElseThrow();

            Optional<UserLocked> last_locked =
                    userLockedRepository.findFirstByUserOrderByLockedFromDesc(user.getPersonal());

            boolean is_locked = false;
            if (last_locked.isPresent())
                is_locked = last_locked.get().getLockedTo().isAfter(LocalDateTime.now());

            return new StoredUserCredit(user, is_locked);
        } catch (NoSuchElementException e){
            throw new UsernameNotFoundException("not found username: " + username);
        }
    }


}
