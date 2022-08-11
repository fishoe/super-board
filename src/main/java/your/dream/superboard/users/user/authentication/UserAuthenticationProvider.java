package your.dream.superboard.users.user.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import your.dream.superboard.authentication.token.CommonAuthenticationToken;
import your.dream.superboard.authentication.password.encoder.PasswordEncoderStorage;
import your.dream.superboard.users.user.service.UserService;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoderStorage pwEncoders;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException
    {
        UserDetails storedUserCredit =
                userService.loadUserByUsername(authentication.getName());

        boolean isAuthenticated = pwEncoders.getPasswordEncoder()
                .matches((String)authentication.getCredentials(),
                        storedUserCredit.getPassword());

        if (isAuthenticated){
            authentication = CommonAuthenticationToken.authenticated(
                    UserAuthenticationToken.class,
                    authentication.getName(),
                    storedUserCredit,
                    storedUserCredit.getAuthorities()
            );
            ((CommonAuthenticationToken)authentication).eraseCredentials();
            return authentication;
        }else
            throw new BadCredentialsException("wrong authentication information");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserAuthenticationToken.class.isAssignableFrom(authentication);
    }
}