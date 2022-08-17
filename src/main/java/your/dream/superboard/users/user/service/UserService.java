package your.dream.superboard.users.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import your.dream.superboard.authentication.password.encoder.PasswordEncoderStorage;
import your.dream.superboard.users.user.authentication.AuthenticationType;
import your.dream.superboard.users.user.authentication.StoredUserCredit;
import your.dream.superboard.users.user.data.UserAuthentication;
import your.dream.superboard.users.user.data.UserLocked;
import your.dream.superboard.users.user.data.UserPersonal;
import your.dream.superboard.users.user.exception.DuplicatedUsername;
import your.dream.superboard.users.user.exception.InvalidJwtSubject;
import your.dream.superboard.users.user.exception.NotSupportedAuthenticationType;
import your.dream.superboard.users.user.repository.UserAuthenticationRepository;
import your.dream.superboard.users.user.repository.UserLockedRepository;
import your.dream.superboard.users.user.repository.UserPersonalRepository;
import your.dream.superboard.users.user.request.UserRequest;
import your.dream.superboard.users.user.response.UserResponse;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserAuthenticationRepository userAuthenticationRepository;
    private final UserLockedRepository userLockedRepository;
    private final UserPersonalRepository userPersonalRepository;
    private final PasswordEncoderStorage pwEncoder;

    public UserPersonal findUser(String jwtSubject) {
        String[] splitSubject = jwtSubject.split("\\|");
        if (splitSubject.length != 2)
            throw new InvalidJwtSubject();
        var authType = AuthenticationType.valueOf(splitSubject[0]);
        var username = splitSubject[1];

        switch (authType) {
            case USER -> {
                return findUserByUsername(username);
            }
            case NAVER, KAKAO -> {
                throw new NotSupportedAuthenticationType();
            }
        }
        throw new NotSupportedAuthenticationType();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserAuthentication user = userAuthenticationRepository.findByUsername(username)
                    .orElseThrow();

            Optional<UserLocked> last_locked =
                    userLockedRepository.findFirstByUserOrderByLockedFromDesc(user.getPersonal());

            boolean is_locked = false;
            if (last_locked.isPresent())
                is_locked = last_locked.get().getLockedTo().isAfter(Instant.now());

            return new StoredUserCredit(user, is_locked);
        } catch (NoSuchElementException e){
            throw new UsernameNotFoundException("not found username: " + username);
        }
    }

    private UserPersonal findUserByUsername(String username) {
        UserAuthentication user = userAuthenticationRepository.findByUsername(username)
                .orElseThrow();
        return user.getPersonal();
    }

    public UserResponse findUserPersonalByAdmin(Long userId){
        UserPersonal user = userPersonalRepository.findById(userId).orElseThrow();
        return new UserResponse(user);
    }

    public UserResponse createUser(UserRequest userRequest){
        if (userAuthenticationRepository.existsByUsername(userRequest.getUsername()))
            throw new DuplicatedUsername(userRequest.getUsername());
        UserPersonal personal = new UserPersonal(userRequest);
        UserAuthentication user = new UserAuthentication(userRequest, personal, pwEncoder);
        userAuthenticationRepository.save(user);
        personal.setUserAuthentication(user);
        return new UserResponse(personal);
    }
}
