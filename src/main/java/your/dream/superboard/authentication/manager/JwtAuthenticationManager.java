package your.dream.superboard.authentication.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {
    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException
    {
        return authentication;
    }

    private List<GrantedAuthority> getAuthorities(Jwt jwt){
        List<String> scope = jwt.getClaimAsStringList("scope");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : scope) {
            authorities.add(role::strip);
        }
        return authorities;
    }
}
